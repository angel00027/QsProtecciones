package mp.quesito.qsProtecciones.listeners.owners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import mp.quesito.qsProtecciones.QsProtecciones;
import mp.quesito.qsProtecciones.mensajes.mensajesjugador;
import mp.quesito.qsProtecciones.menus.players.MenuFlagsWG;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ListenerFlagsAdm implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player jugador)) return;
        Inventory inv = event.getInventory();

        if (event.getView() == null || !event.getView().getTitle().startsWith("§6⚑ Flags de §e")) return;

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        String nombre = clickedItem.getItemMeta().getDisplayName();
        if (nombre == null) return;

        if (clickedItem.getType() == org.bukkit.Material.BARRIER && nombre.contains("Cerrar menú")) {
            jugador.closeInventory();
            jugador.playSound(jugador.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            return;
        }

        String regionId = MenuFlagsWG.getRegionAbierta(jugador.getUniqueId());
        if (regionId == null) {
            jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "region_no_encontrada"));
            jugador.closeInventory();
            return;
        }

        var world = jugador.getWorld();
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
        if (regionManager == null) {
            jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "error_no_gestor_regiones"));
            jugador.closeInventory();
            return;
        }

        ProtectedRegion region = regionManager.getRegion(regionId);
        if (region == null) {
            jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "error_no_region", Map.of("region", regionId)));
            jugador.closeInventory();
            return;
        }

        for (var entry : MenuFlagsWG.getFlagSlots().entrySet()) {
            int slot = entry.getKey();
            StateFlag flag = entry.getValue();

            if (slot == event.getRawSlot()) {
                State currentState = region.getFlag(flag);
                State newState;

                if (currentState == null) {
                    newState = State.ALLOW;
                } else if (currentState == State.ALLOW) {
                    newState = State.DENY;
                } else {
                    newState = null;
                }

                region.setFlag(flag, newState);

                String estadoTexto;
                if (newState == null) {
                    estadoTexto = mensajesjugador.obtenerPlano(jugador, "estado_none");

                } else if (newState == State.ALLOW) {

                    estadoTexto = mensajesjugador.obtenerPlano(jugador, "estado_allow");
                } else {

                    estadoTexto = mensajesjugador.obtenerPlano(jugador, "estado_deny");
                }

                jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "modificada", Map.of(
                        "flag", flag.getName(),
                        "estado", estadoTexto
                )));

                jugador.playSound(jugador.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.5f);

                Bukkit.getScheduler().runTaskLater(QsProtecciones.getInstance(), () -> {
                    MenuFlagsWG.abrir(jugador, regionId);
                }, 1L);

                return;
            }
        }
    }
}

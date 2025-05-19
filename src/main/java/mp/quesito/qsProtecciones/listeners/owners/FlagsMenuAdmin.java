package mp.quesito.qsProtecciones.listeners.owners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import mp.quesito.qsProtecciones.QsProtecciones;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import mp.quesito.qsProtecciones.menus.players.MenuFlagsWG;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class FlagsMenuAdmin implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player jugador)) return;

        String titulo = event.getView().getTitle();
        String tituloSinColor = ChatColor.stripColor(titulo);

        if (!tituloSinColor.startsWith("Flags de ")) return; // Solo nuestro menú

        event.setCancelled(true); // Cancelamos para que no muevan items

        int slot = event.getRawSlot();
        if (slot < 0) return;

        if (!MenuFlagsWG.getFlagSlots().containsKey(slot)) return; // No es slot de flag

        String regionId = MenuFlagsWG.getRegionAbierta(jugador.getUniqueId());
        if (regionId == null) {
            jugador.sendMessage(mensajesnormales.obtenerComponent("error_no_region"));
            jugador.closeInventory();
            return;
        }

        var world = jugador.getWorld();
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
        if (regionManager == null) {
            jugador.sendMessage(mensajesnormales.obtenerComponent("error_no_gestor_regiones"));
            return;
        }

        ProtectedRegion region = regionManager.getRegion(regionId);
        if (region == null) {
            jugador.sendMessage(mensajesnormales.obtenerComponent("error_no_region_id",
                    Map.of("region", regionId)));
            return;
        }

        StateFlag flag = MenuFlagsWG.getFlagSlots().get(slot);
        if (flag == null) return;

        // Obtener el estado actual
        var currentState = region.getFlag(flag);

        // Alternar el estado: null -> DENY -> ALLOW -> null
        var nuevoEstado = switch (currentState) {
            case null -> StateFlag.State.DENY;
            case StateFlag.State.DENY -> StateFlag.State.ALLOW;
            case StateFlag.State.ALLOW -> null;
            default -> null;
        };

        // Establecer la flag con el nuevo estado
        region.setFlag(flag, nuevoEstado);

        // Guardar cambios
        try {
            regionManager.save();
        } catch (Exception e) {
            jugador.sendMessage(mensajesnormales.obtenerComponent("error_guardar_cambios"));
            e.printStackTrace();
            return;
        }

        // Traducción del estado
        String estadoTraducido = traducirEstado(nuevoEstado);

        jugador.sendMessage(mensajesnormales.obtenerComponent("flag_cambiada",
                Map.of("flag", flag.getName(),
                        "estado", estadoTraducido)));

        Bukkit.getScheduler().runTaskLater(QsProtecciones.getInstance(), () -> {
            MenuFlagsWG.abrir(jugador, regionId);
        }, 1L);

    }

    private String traducirEstado(StateFlag.State estado) {
        if (estado == null) return mensajesnormales.obtener("estado_none");
        switch (estado) {
            case DENY ->  { return mensajesnormales.obtener("estado_deny"); }
            case ALLOW -> { return mensajesnormales.obtener("estado_allow"); }
            default ->   { return estado.toString(); }
        }
    }
}

package mp.quesito.qsProtecciones.listeners.players;

import dev.espi.protectionstones.PSProtectBlock;
import dev.espi.protectionstones.PSRegion;
import dev.espi.protectionstones.ProtectionStones;
import mp.quesito.qsProtecciones.managers.players.ConfirmacionManager;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class ConfirmacionEliminarListener implements Listener {

    @EventHandler
    public void onConfirmacionClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player jugador)) return;

        String title = event.getView().getTitle();
        if (!title.equals(ChatColor.RED + "Confirmar eliminación")) return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        Material tipo = clicked.getType();
        UUID jugadorUUID = jugador.getUniqueId();

        if (tipo == Material.GREEN_WOOL) {
            // Confirmar eliminación
            if (!ConfirmacionManager.contains(jugadorUUID)) {
                jugador.sendMessage(mensajesnormales.obtenerComponent("no_region_pendiente"));
                jugador.closeInventory();
                return;
            }

            String regionId = ConfirmacionManager.get(jugadorUUID);
            ConfirmacionManager.remove(jugadorUUID);

            World mundo = jugador.getWorld();
            List<PSRegion> regiones = ProtectionStones.getPSRegions(mundo, regionId);

            if (regiones.isEmpty()) {
                jugador.sendMessage(mensajesnormales.obtenerComponent("region_no_encontrada"));
            } else {
                PSRegion region = regiones.get(0);

                PSProtectBlock tipoBloque = region.getTypeOptions();

                if (tipoBloque != null) {
                    // Usamos el método oficial para crear el bloque de protección correctamente
                    ItemStack item = ProtectionStones.createProtectBlockItem(tipoBloque);
                    jugador.getInventory().addItem(item);
                }

                region.deleteRegion(true, jugador);
                jugador.sendMessage(mensajesnormales.obtenerComponent("region_eliminada_exito"));
            }
            jugador.closeInventory();

        } else if (tipo == Material.RED_WOOL) {
            ConfirmacionManager.remove(jugadorUUID);
            jugador.sendMessage(mensajesnormales.obtenerComponent("eliminacion_cancelada"));
            jugador.closeInventory();
        }
    }
}

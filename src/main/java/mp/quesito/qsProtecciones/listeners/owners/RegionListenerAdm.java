package mp.quesito.qsProtecciones.listeners.owners;

import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.menus.owners.MenuEliminacionHolder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegionListenerAdm implements Listener {

    private final Map<UUID, String> confirmacionRegionMap = new HashMap<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player jugador)) return;

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        String invTitle = event.getView().getTitle();

        if (invTitle.equalsIgnoreCase("Tu título original del inventario")) {
            event.setCancelled(true);
            Material tipo = clicked.getType();
            if (tipo == Material.RED_WOOL) {
                // TODO: implementar lógica para obtener la región que el jugador quiere eliminar
                PSRegion region = null;

                if (region != null) {
                    confirmacionRegionMap.put(jugador.getUniqueId(), region.getId());

                    jugador.closeInventory();
                    MenuEliminacionHolder.abrir(jugador, region);

                } else {
                    jugador.sendMessage("No se pudo determinar la región para eliminar.");
                }
            }
        }

    }

    // Agregar getter para confirmacionRegionMap para el siguiente listener si quieres
    public Map<UUID, String> getConfirmacionRegionMap() {
        return confirmacionRegionMap;
    }
}
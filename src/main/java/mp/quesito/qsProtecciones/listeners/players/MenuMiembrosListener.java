package mp.quesito.qsProtecciones.listeners.players;

import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.managers.players.MemberManager;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import mp.quesito.qsProtecciones.menus.players.MenuAccionesProteccion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Map;

public class MenuMiembrosListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player jugador)) return;
        if (!event.getView().getTitle().equals(ChatColor.AQUA + "Miembros de la Región")) return;

        event.setCancelled(true);
        var item = event.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        PSRegion region = MemberManager.getRegion(jugador.getUniqueId());
        if (region == null) {
            jugador.sendMessage(mensajesnormales.obtenerComponent("error_recuperar_region"));
            jugador.closeInventory();
            return;
        }

        switch (item.getType()) {
            case LIME_WOOL -> {
                jugador.closeInventory();
                MemberManager.setEsperandoNombre(jugador.getUniqueId(), true);
                jugador.sendMessage(mensajesnormales.obtenerComponent("mensaje_ingresar_nombre"));
            }
            case PLAYER_HEAD -> {
                if (!(item.getItemMeta() instanceof SkullMeta meta)) return;
                OfflinePlayer offlinePlayer = meta.getOwningPlayer();
                if (offlinePlayer == null) return;

                region.removeMember(offlinePlayer.getUniqueId());
                jugador.sendMessage(mensajesnormales.obtenerComponent("miembro_eliminado",
                        Map.of("nombre", offlinePlayer.getName() != null ? offlinePlayer.getName() : "Desconocido")));
                MemberManager.abrirMenu(jugador, region); // Refrescar menú
            }
            case ARROW -> {
                // Botón regresar
                jugador.closeInventory();
                MenuAccionesProteccion.abrir(jugador, region);
            }
        }
    }
}

package mp.quesito.qsProtecciones.listeners.owners;

import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.managers.players.RenameManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class RenameListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!RenameManager.estaRenombrando(event.getPlayer().getUniqueId())) return;

        event.setCancelled(true); // Cancelar que se envíe el mensaje al chat normal

        String nuevoNombre = event.getMessage().trim();
        if (nuevoNombre.equalsIgnoreCase("cancelar")) {
            RenameManager.removeJugadorRenombrando(event.getPlayer().getUniqueId());
            event.getPlayer().sendMessage(ChatColor.RED + "Cambio de nombre cancelado.");
            return;
        }

        PSRegion region = RenameManager.getRegionRenombrando(event.getPlayer().getUniqueId());
        if (region == null) {
            event.getPlayer().sendMessage(ChatColor.RED + "Error: No se encontró la región para renombrar.");
            RenameManager.removeJugadorRenombrando(event.getPlayer().getUniqueId());
            return;
        }

        // Aquí puedes validar que el nuevo nombre cumpla requisitos (longitud, caracteres, etc)
        if (nuevoNombre.length() > 30) {
            event.getPlayer().sendMessage(ChatColor.RED + "El nombre es demasiado largo. Máximo 30 caracteres.");
            return;
        }

        // Cambiar el nombre de la región
        region.setName(nuevoNombre);

        event.getPlayer().sendMessage(ChatColor.GREEN + "Nombre de la región cambiado a: " + ChatColor.AQUA + nuevoNombre);
        RenameManager.removeJugadorRenombrando(event.getPlayer().getUniqueId());
    }
}

package mp.quesito.qsProtecciones.listeners.owners;

import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.managers.players.MemberManager;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;

public class AgregarMiembroAdm implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player jugador = event.getPlayer();
        if (!MemberManager.estaEsperandoNombre(jugador.getUniqueId())) return;

        event.setCancelled(true);
        String nombre = event.getMessage().trim();

        MemberManager.setEsperandoNombre(jugador.getUniqueId(), false);
        PSRegion region = MemberManager.getRegion(jugador.getUniqueId());

        if (region == null) {
            // En el evento, para enviar mensaje:
            jugador.sendMessage(mensajesnormales.obtenerComponent("region_no_encontrada"));
            return;
        }

        if (nombre.equalsIgnoreCase("cancelar")) {
            // En el evento, para enviar mensaje:
            jugador.sendMessage(mensajesnormales.obtenerComponent("accion_cancelada"));
            return;
        }

        OfflinePlayer objetivo = Bukkit.getOfflinePlayer(nombre);

        if (!objetivo.hasPlayedBefore()) {
            jugador.sendMessage(mensajesnormales.obtenerComponent("jugador_no_ha_entrado"));
        }

        if (region.getMembers().contains(objetivo.getUniqueId())) {
            jugador.sendMessage(mensajesnormales.obtenerComponent("jugador_ya_miembro"));
            return;
        }

        region.addMember(objetivo.getUniqueId());

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("jugador", objetivo.getName());

        jugador.sendMessage(mensajesnormales.obtenerComponent("miembro_agregado", placeholders));

    }
}

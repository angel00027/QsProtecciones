package mp.quesito.qsProtecciones.listeners.players;

import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.mensajes.mensajesjugador;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import mp.quesito.qsProtecciones.menus.players.MenuProteccionesJugador;
import mp.quesito.qsProtecciones.menus.players.MenuSeleccionProteccion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.UUID;

public class MenuProteccionesListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player jugador)) return;

        String rawTitle = mensajesjugador.obtenerPlano(jugador, "menu_tus_protecciones"); // ej: "§a» §bTus Protecciones"
        Component expectedTitle = LegacyComponentSerializer.legacySection().deserialize(rawTitle);

        Component actualTitle = event.getView().title();

        if (!actualTitle.equals(expectedTitle)) return;

        event.setCancelled(true);

        int slot = event.getRawSlot();
        List<PSRegion> regiones = MenuProteccionesJugador.getRegionesDelJugador(jugador.getUniqueId());

        if (slot < 0 || slot >= regiones.size()) return;

        PSRegion region = regiones.get(slot);
        UUID jugadorUUID = jugador.getUniqueId();
        List<UUID> owners = region.getOwners();

        jugador.closeInventory();
        MenuSeleccionProteccion.abrir(jugador, region); // ⬅️ Abre el submenú
    }
}

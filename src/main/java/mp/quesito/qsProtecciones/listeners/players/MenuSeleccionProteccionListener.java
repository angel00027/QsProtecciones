package mp.quesito.qsProtecciones.listeners.players;
import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.mensajes.mensajesjugador;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import mp.quesito.qsProtecciones.menus.players.MenuAccionesProteccion;
import mp.quesito.qsProtecciones.menus.players.MenuProteccionesJugador;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MenuSeleccionProteccionListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player jugador)) return;

        // Obtener región del mapa
        PSRegion region = MenuProteccionesJugador.regionAbiertaPorJugador.get(jugador.getUniqueId());
        if (region == null) return;

        // Generar el título real esperado desde mensajes usando MiniMessage
        Component esperadoComponent = mensajesjugador.obtenerComponent(jugador, "menu_seleccion", Map.of("region", region.getId()));

        // Convertir ambos títulos a texto plano para comparar
        String esperado = PlainTextComponentSerializer.plainText().serialize(esperadoComponent);
        String actual = PlainTextComponentSerializer.plainText().serialize(event.getView().title());

        if (!actual.equals(esperado)) return;

        event.setCancelled(true);



        Material tipo = event.getCurrentItem() != null ? event.getCurrentItem().getType() : Material.AIR;

        switch (tipo) {
            case ENDER_PEARL -> {
                var loc = region.getHome();
                if (loc == null) {
                    jugador.sendMessage(mensajesnormales.obtenerComponent("no_tp"));
                    return;
                }
                jugador.closeInventory();
                jugador.teleport(loc);
                jugador.sendMessage(
                        mensajesnormales.obtenerComponent(
                                "tp_success",
                                Map.of("region", region.getId())
                        )
                );

                jugador.playSound(jugador.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

            }

            case COMMAND_BLOCK -> {
                UUID jugadorUUID = jugador.getUniqueId();
                List<UUID> owners = region.getOwners();

                if (owners == null || !owners.contains(jugadorUUID)) {
                    jugador.sendMessage(mensajesnormales.obtenerComponent("no_permissions"));

                    jugador.closeInventory();
                    return;
                }

                jugador.closeInventory();
                jugador.sendMessage(mensajesnormales.obtenerComponent(
                        "configuring_protection",
                        Map.of("region", region.getId())
                ));
                MenuAccionesProteccion.abrir(jugador, region); // Si tienes un tercer menú
            }

        }

        MenuProteccionesJugador.regionAbiertaPorJugador.remove(jugador.getUniqueId());
    }
}


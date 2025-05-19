package mp.quesito.qsProtecciones.listeners.players;

import dev.espi.protectionstones.PSRegion;

import mp.quesito.qsProtecciones.managers.players.ConfirmacionManager;
import mp.quesito.qsProtecciones.managers.players.MemberManager;
import mp.quesito.qsProtecciones.managers.players.RenameManager;
import mp.quesito.qsProtecciones.menus.players.MenuConfirmacionEliminar;
import mp.quesito.qsProtecciones.menus.players.MenuFlagsWG;
import mp.quesito.qsProtecciones.menus.players.MenuProteccionesJugador;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;


public class MenuAccionesProteccionListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player jugador)) return;

        // Obtener el título como texto plano
        String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());

        if (!title.startsWith("Acciones: ")) return;

        event.setCancelled(true);

        // Obtener el ID de la región (sin colores)
        String regionId = title.replace("Acciones: ", "");

        // Obtener regiones del jugador
        List<PSRegion> regiones = MenuProteccionesJugador.getRegionesDelJugador(jugador.getUniqueId());
        if (regiones.isEmpty()) return;

        // Buscar la región con ese ID
        PSRegion region = regiones.stream()
                .filter(r -> r.getId().equals(regionId))
                .findFirst()
                .orElse(null);

        if (region == null) {
            jugador.sendMessage(Component.text("No se encontró la región.", NamedTextColor.RED));
            return;
        }

        ItemStack item = event.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        Material tipo = item.getType();

        switch (tipo) {
            case ENDER_PEARL -> {
                if (region.getHome() != null) {
                    jugador.teleport(region.getHome());
                    jugador.playSound(jugador.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                    jugador.sendMessage(Component.text("Teletransportado a la protección.", NamedTextColor.GREEN));
                } else {
                    jugador.sendMessage(Component.text("La protección no tiene un punto de home establecido.", NamedTextColor.RED));
                }
            }

            case LEVER -> {
                jugador.playSound(jugador.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                MenuFlagsWG.abrir(jugador, regionId);
            }

            case PLAYER_HEAD -> {
                jugador.playSound(jugador.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                MemberManager.abrirMenu(jugador, region);
            }

            case NAME_TAG -> {
                ConfirmacionManager.remove(jugador.getUniqueId()); // Solo por asegurar que no haya confirmaciones abiertas
                jugador.closeInventory();
                RenameManager.addJugadorRenombrando(jugador.getUniqueId(), region);
                jugador.sendMessage(Component.text(
                        "Escribe en el chat el nuevo nombre para la región. Escribe 'cancelar' para cancelar.",
                        NamedTextColor.YELLOW));
            }

            case BOOK -> {
                jugador.sendMessage(Component.text("Información de la región:", NamedTextColor.AQUA));
                jugador.sendMessage(Component.text("ID: " + region.getId(), NamedTextColor.GRAY));
                jugador.sendMessage(Component.text("Mundo: " + region.getWorld().getName(), NamedTextColor.GRAY));

                List<UUID> miembros = region.getMembers();
                if (miembros.isEmpty()) {
                    jugador.sendMessage(Component.text("Miembros: Ninguno", NamedTextColor.GRAY));
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (UUID memberUUID : miembros) {
                        sb.append(memberUUID.toString()).append(", ");
                    }
                    // Quitamos la última coma y espacio
                    String miembrosStr = sb.substring(0, sb.length() - 2);
                    jugador.sendMessage(Component.text("Miembros: " + miembrosStr, NamedTextColor.GRAY));
                }

                jugador.closeInventory();  // Cerrar inventario tras mostrar la información
            }

            case MAP -> {
                MenuProteccionesJugador.mostrarProteccionSiEsta(jugador);
                jugador.closeInventory();
            }

            case RED_WOOL -> {
                jugador.closeInventory();
                ConfirmacionManager.put(jugador.getUniqueId(), region.getId());
                jugador.openInventory(MenuConfirmacionEliminar.getMenuConfirmacion());
            }
        }
    }
}
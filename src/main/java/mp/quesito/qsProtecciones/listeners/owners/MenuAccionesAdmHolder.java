package mp.quesito.qsProtecciones.listeners.owners;

import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.managers.owners.AbstractMenuHolder;
import mp.quesito.qsProtecciones.managers.players.ConfirmacionManager;
import mp.quesito.qsProtecciones.managers.players.MemberManager;
import mp.quesito.qsProtecciones.managers.players.RenameManager;
import mp.quesito.qsProtecciones.mensajes.mensajesjugador;
import mp.quesito.qsProtecciones.menus.owners.MenuEliminacionHolder;
import mp.quesito.qsProtecciones.menus.players.MenuConfirmacionEliminar;
import mp.quesito.qsProtecciones.menus.players.MenuFlagsWG;
import mp.quesito.qsProtecciones.menus.players.MenuProteccionesJugador;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class MenuAccionesAdmHolder extends AbstractMenuHolder {

    private final PSRegion region;

    public MenuAccionesAdmHolder(PSRegion region) {
        this.region = region;
    }

    @Override
    public void manejarClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player jugador)) return;

        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        Material tipo = item.getType();

        switch (tipo) {
            case ENDER_PEARL -> {
                if (region.getHome() != null) {
                    jugador.teleport(region.getHome());
                    jugador.playSound(jugador.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                    jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "teleport_exitoso"));
                } else {
                    jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "no_home"));
                }
            }

            case LEVER -> {
                jugador.playSound(jugador.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                MenuFlagsWG.abrir(jugador, region.getId());
            }

            case PLAYER_HEAD -> {
                jugador.playSound(jugador.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                MemberManager.abrirMenu(jugador, region);
            }

            case NAME_TAG -> {
                ConfirmacionManager.remove(jugador.getUniqueId());
                jugador.closeInventory();
                RenameManager.addJugadorRenombrando(jugador.getUniqueId(), region);
                jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "rename_instrucciones"));
            }

            case PAPER -> {
                jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "titulo"));
                jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "id", Map.of("id", region.getId())));
                jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "mundos", Map.of("mundo", region.getWorld().getName())));

                // Miembros con nombres
                List<UUID> miembros = region.getMembers();
                if (miembros.isEmpty()) {
                    jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "miembros_vacio"));
                } else {
                    String miembrosStr = miembros.stream()
                            .map(uuid -> {
                                String name = Bukkit.getOfflinePlayer(uuid).getName();
                                return name != null ? name : uuid.toString();
                            })
                            .reduce((a, b) -> a + ", " + b)
                            .orElse("");
                    jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "miembros_lista", Map.of("miembros", miembrosStr)));
                }

                jugador.closeInventory();
            }


            case MAP -> {
                MenuProteccionesJugador.mostrarProteccionSiEsta(jugador);
                jugador.closeInventory();
            }

            case RED_WOOL -> {
                jugador.closeInventory();
                MenuEliminacionHolder.abrir(jugador, region);

            }
        }
    }
}

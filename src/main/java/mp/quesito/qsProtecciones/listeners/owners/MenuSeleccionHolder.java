package mp.quesito.qsProtecciones.listeners.owners;
import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.managers.owners.AbstractMenuHolder;
import mp.quesito.qsProtecciones.mensajes.mensajesjugador;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import mp.quesito.qsProtecciones.menus.owners.MenuAccionesAdm;
import mp.quesito.qsProtecciones.menus.owners.MenuAdmin;
import mp.quesito.qsProtecciones.menus.players.MenuProteccionesJugador;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;


public class MenuSeleccionHolder extends AbstractMenuHolder {

    private final PSRegion region;

    public MenuSeleccionHolder(PSRegion region) {
        this.region = region;
    }

    public PSRegion getRegion() {
        return region;
    }

    @Override
    public void manejarClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player jugador)) return;

        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType().isAir()) return;

        switch (item.getType()) {
            case ENDER_PEARL -> {
                var loc = region.getHome();
                if (loc == null) {
                    jugador.sendMessage(mensajesnormales.obtenerComponent("no_tp"));
                    return;
                }
                jugador.closeInventory();
                jugador.teleport(loc);
                jugador.sendMessage(mensajesnormales.obtenerComponent("tp_success", Map.of("region", region.getId())));
                jugador.playSound(jugador.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            }

            case COMMAND_BLOCK -> {
                MenuAccionesAdm.abrir(jugador, region);
            }
        }
    }
}

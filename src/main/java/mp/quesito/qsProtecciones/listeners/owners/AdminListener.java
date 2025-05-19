package mp.quesito.qsProtecciones.listeners.owners;

import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.mensajes.mensajesjugador;
import mp.quesito.qsProtecciones.menus.owners.MenuAdmin;
import mp.quesito.qsProtecciones.menus.owners.MenuSeleccion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AdminListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player jugador)) return;

        // Obtenemos el título esperado del menú
        String rawTitle = mensajesjugador.obtenerPlano(jugador, "menu_protecciones"); // ej: "§a» §bTus Protecciones"
        Component expectedTitle = LegacyComponentSerializer.legacySection().deserialize(rawTitle);
        Component actualTitle = event.getView().title();

        // Verificamos que el menú sea el que esperamos
        if (!actualTitle.equals(expectedTitle)) return;

        event.setCancelled(true); // Cancelamos el clic para evitar comportamiento por defecto

        int slot = event.getRawSlot();

        // Validación del slot dentro de los límites del inventario
        if (slot < 0 || slot >= event.getInventory().getSize()) return;

        // Obtenemos la región correspondiente
        PSRegion region = MenuAdmin.getRegionEnSlot(slot);

        // Cerramos el inventario para evitar más clics
        jugador.closeInventory();

        if (region == null) {
            jugador.sendMessage(Component.text("§cNo hay ninguna región asociada a este ítem."));
            return;
        }

        // Abre el submenú de selección
        MenuSeleccion.abrir(jugador, region);
    }
}

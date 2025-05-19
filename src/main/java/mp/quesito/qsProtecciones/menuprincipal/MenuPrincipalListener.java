package mp.quesito.qsProtecciones.menuprincipal;

import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import mp.quesito.qsProtecciones.menus.owners.MenuAdmin;
import mp.quesito.qsProtecciones.menus.players.MenuProteccionesJugador;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MenuPrincipalListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {


        Player jugador = (Player) event.getWhoClicked();

        // Obtener título esperado desde mensajesnormales
        String rawTitle = mensajesnormales.obtener("menu_principal_titulo");
        rawTitle = "        " + rawTitle + "        ";
        Component expectedTitle = LegacyComponentSerializer.legacySection().deserialize(rawTitle);

        Component actualTitle = event.getView().title();

        if (!actualTitle.equals(expectedTitle)) return;

        event.setCancelled(true);


        ItemStack item = event.getCurrentItem();

        if (item == null || item.getType() == Material.AIR) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return;

        String nombre = ChatColor.stripColor(meta.getDisplayName());

        // Obtén los nombres de los ítems desde mensajesnormales o directamente en el listener
        String verProteccionesName = ChatColor.stripColor(mensajesnormales.obtener("menu_ver_protecciones_name"));
        String estadisticasName = ChatColor.stripColor(mensajesnormales.obtener("menu_estadisticas_name"));
        String adminPanelName = ChatColor.stripColor(mensajesnormales.obtener("menu_admin_name"));

        if (item.getType() == Material.GRASS_BLOCK && nombre.contains(verProteccionesName)) {
            jugador.sendMessage(mensajesnormales.obtener("abriendo_protecciones"));

            MenuProteccionesJugador.abrir(jugador);

        } else if (item.getType() == Material.IRON_DOOR && nombre.contains(estadisticasName)) {
            jugador.sendMessage(mensajesnormales.obtener("estadisticas_titulo").replace("{jugador}", jugador.getName()));

            List<PSRegion> regiones = MenuProteccionesJugador.getRegionesDelJugador(jugador.getUniqueId());

            if (regiones == null || regiones.isEmpty()) {
                jugador.sendMessage(mensajesnormales.obtener("no_tienes_protecciones"));
                return;
            }

            jugador.sendMessage(mensajesnormales.obtener("total_protecciones").replace("{total}", String.valueOf(regiones.size())));
            jugador.sendMessage(mensajesnormales.obtener("detalles"));

            for (PSRegion region : regiones) {
                String mundo = region.getWorld().getName();
                String id = region.getId();
                int miembros = region.getMembers().size();

                String linea = mensajesnormales.obtener("detalle_proteccion")
                        .replace("{id}", id)
                        .replace("{mundo}", mundo)
                        .replace("{miembros}", String.valueOf(miembros));

                jugador.sendMessage(linea);
            }

        } else if (item.getType() == Material.NETHER_STAR && nombre.contains(adminPanelName)) {
            if (jugador.hasPermission("proteccionqs.admin")) {
                jugador.sendMessage(mensajesnormales.obtener("accediendo_panel_admin"));
                // Aquí abres el menú de administración si tienes
                MenuAdmin.abrir(jugador);
            } else {
                jugador.sendMessage(mensajesnormales.obtener("no_permiso_panel_admin"));
            }
        }
    }
}

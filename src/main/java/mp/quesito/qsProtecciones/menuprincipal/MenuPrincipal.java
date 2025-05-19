package mp.quesito.qsProtecciones.menuprincipal;

import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import mp.quesito.qsProtecciones.menus.players.MenuProteccionesJugador;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuPrincipal {

    public static void abrir(Player jugador) {

        int size = 27; // 3 filas

        // Obtener título desde mensajesnormales
        String rawTitle = mensajesnormales.obtener("menu_principal_titulo");
        // Agregar espacios si quieres centrar (opcional)
        rawTitle = "        " + rawTitle + "        ";

        Component titleComponent = LegacyComponentSerializer.legacySection().deserialize(rawTitle);

        Inventory menu = Bukkit.createInventory(null, size, titleComponent);

        // Ítem "Ver todas tus protecciones"
        String verName = mensajesnormales.obtener("menu_ver_protecciones_name");
        List<String> verLore = mensajesnormales.obtenerListaConColores("menu_ver_protecciones_lore");

        ItemStack verProtecciones = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta metaVer = verProtecciones.getItemMeta();
        if (metaVer != null) {
            metaVer.setDisplayName(verName);
            metaVer.setLore(verLore);
            verProtecciones.setItemMeta(metaVer);
        }

        // Ítem "Estadísticas de protecciones"
        String estadisticasName = mensajesnormales.obtener("menu_estadisticas_name");
        List<String> estadisticasLoreRaw = mensajesnormales.obtenerListaConColores("menu_estadisticas_lore");

        var regiones = MenuProteccionesJugador.getRegionesDelJugador(jugador.getUniqueId());
        int totalProtecciones = regiones == null ? 0 : regiones.size();

        // Reemplazar placeholder {total} en lore de estadísticas
        List<String> estadisticasLore = new ArrayList<>();
        for (String line : estadisticasLoreRaw) {
            estadisticasLore.add(line.replace("{total}", String.valueOf(totalProtecciones)));
        }

        ItemStack configurarProteccion = new ItemStack(Material.IRON_DOOR);
        ItemMeta metaConfig = configurarProteccion.getItemMeta();
        if (metaConfig != null) {
            metaConfig.setDisplayName(estadisticasName);
            metaConfig.setLore(estadisticasLore);
            configurarProteccion.setItemMeta(metaConfig);
        }

        // Ítem decorativo para bordes
        ItemStack borde = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemMeta metaBorde = borde.getItemMeta();
        if (metaBorde != null) {
            metaBorde.setDisplayName(" ");
            borde.setItemMeta(metaBorde);
        }

        for (int i = 0; i < size; i++) {
            if (i != 11 && i != 15 && i != 13) {
                menu.setItem(i, borde);
            }
        }

        menu.setItem(11, verProtecciones);
        menu.setItem(15, configurarProteccion);

        // Ítem panel admin o cristal según permiso
        if (jugador.hasPermission("proteccionqs.admin")) {
            String adminName = mensajesnormales.obtener("menu_admin_name");
            List<String> adminLore = mensajesnormales.obtenerListaConColores("menu_admin_lore");

            ItemStack adminPanel = new ItemStack(Material.NETHER_STAR);
            ItemMeta metaAdmin = adminPanel.getItemMeta();
            if (metaAdmin != null) {
                metaAdmin.setDisplayName(adminName);
                metaAdmin.setLore(adminLore);
                adminPanel.setItemMeta(metaAdmin);
            }
            menu.setItem(13, adminPanel);
        } else {
            String cristalName = mensajesnormales.obtener("menu_cristal_name");

            ItemStack cristal = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
            ItemMeta metaCristal = cristal.getItemMeta();
            if (metaCristal != null) {
                metaCristal.setDisplayName(cristalName);
                cristal.setItemMeta(metaCristal);
            }
            menu.setItem(13, cristal);
        }

        jugador.openInventory(menu);
    }
}

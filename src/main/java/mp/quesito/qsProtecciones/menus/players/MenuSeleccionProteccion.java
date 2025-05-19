package mp.quesito.qsProtecciones.menus.players;

import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.mensajes.mensajesjugador;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.UUID;

public class MenuSeleccionProteccion {

    public static void abrir(Player jugador, PSRegion region) {

        int size = 27;
        Component titleComp = mensajesjugador.obtenerComponent(
                jugador,
                "menu_seleccion",
                Map.of("region", region.getId())
        );

        Inventory menu = Bukkit.createInventory(null, size, titleComp);

        // Opción de teletransporte
        ItemStack tp = new ItemStack(Material.ENDER_PEARL);
        ItemMeta tpMeta = tp.getItemMeta();
        tpMeta.setDisplayName(mensajesnormales.obtener("menu_option_teleport"));
        tp.setItemMeta(tpMeta);
        menu.setItem(12, tp);

        // Opción de configuración
        ItemStack config = new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta configMeta = config.getItemMeta();
        configMeta.setDisplayName(mensajesnormales.obtener("menu_option_configure"));
        config.setItemMeta(configMeta);
        menu.setItem(14, config);

        MenuProteccionesJugador.regionAbiertaPorJugador.put(jugador.getUniqueId(), region);
        jugador.openInventory(menu);
    }
}

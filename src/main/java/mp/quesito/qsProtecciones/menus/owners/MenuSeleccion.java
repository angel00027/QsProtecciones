package mp.quesito.qsProtecciones.menus.owners;

import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.listeners.owners.MenuSeleccionHolder;
import mp.quesito.qsProtecciones.mensajes.mensajesjugador;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import mp.quesito.qsProtecciones.menus.players.MenuProteccionesJugador;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class MenuSeleccion {

    public static void abrir(Player jugador, PSRegion region) {
        int size = 27;

        Component titleComp = mensajesjugador.obtenerComponent(
                jugador,
                "menu_seleccion_adm",
                Map.of("region", region.getId())
        );

        Inventory menu = Bukkit.createInventory(new MenuSeleccionHolder(region), size, titleComp);

        // Opción de teletransporte
        ItemStack tp = new ItemStack(Material.ENDER_PEARL);
        var tpMeta = tp.getItemMeta();
        tpMeta.setDisplayName(mensajesnormales.obtener("menu_option_teleport"));
        tp.setItemMeta(tpMeta);
        menu.setItem(12, tp);

        // Opción de configuración
        ItemStack config = new ItemStack(Material.COMMAND_BLOCK);
        var configMeta = config.getItemMeta();
        configMeta.setDisplayName(mensajesnormales.obtener("menu_option_configure"));
        config.setItemMeta(configMeta);
        menu.setItem(14, config);

        jugador.openInventory(menu);
    }
}


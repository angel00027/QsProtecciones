package mp.quesito.qsProtecciones.menus.owners;

import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.listeners.owners.MenuAccionesAdmHolder;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class MenuAccionesAdm {

    public static void abrir(Player admin, PSRegion region) {
        // Obtener el título desde mensajes (por ejemplo, clave "menu_acciones_titulo" con un placeholder "region")
        Component titulo = mensajesnormales.obtenerComponent("menu_acciones_titulo", Map.of("region", region.getId()));

        Inventory menu = Bukkit.createInventory(new MenuAccionesAdmHolder(region), 27, titulo);

        menu.setItem(10, crearItem(Material.ENDER_PEARL, mensajesnormales.obtenerComponent("teletransporte")));
        menu.setItem(11, crearItem(Material.LEVER, mensajesnormales.obtenerComponent("flags")));
        menu.setItem(12, crearItem(Material.PLAYER_HEAD, mensajesnormales.obtenerComponent("miembros")));
        menu.setItem(13, crearItem(Material.NAME_TAG, mensajesnormales.obtenerComponent("renombrar")));
        menu.setItem(14, crearItem(Material.PAPER, mensajesnormales.obtenerComponent("informacion")));
        menu.setItem(15, crearItem(Material.MAP, mensajesnormales.obtenerComponent("visualizar")));
        menu.setItem(16, crearItem(Material.RED_WOOL, mensajesnormales.obtenerComponent("eliminar"), NamedTextColor.RED));

        admin.openInventory(menu);
    }

    private static ItemStack crearItem(Material material, Component nombre) {
        return crearItem(material, nombre, NamedTextColor.YELLOW);
    }

    private static ItemStack crearItem(Material material, Component nombre, NamedTextColor color) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        // Aplica el color solo si el Component no lo tiene definido
        Component nombreColoreado = nombre.color() == null ? nombre.color(color) : nombre;

        meta.displayName(nombreColoreado);
        item.setItemMeta(meta);
        return item;
    }
}

package mp.quesito.qsProtecciones.menus.players;

import dev.espi.protectionstones.PSRegion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuAccionesProteccion {

    public static void abrir(Player jugador, PSRegion region) {
        Inventory menu = Bukkit.createInventory(null, 27, "§bAcciones: " + region.getId());

        // TP a la región
        menu.setItem(10, crearItem(Material.ENDER_PEARL, "§aTP a la región", "§7Teletranspórtate a la región si tienes permiso."));

        // Modificar flags
        menu.setItem(11, crearItem(Material.LEVER, "§eModificar Flags", "§7Activa o desactiva flags comunes."));

        // Agregar / quitar miembros
        menu.setItem(12, crearItem(Material.PLAYER_HEAD, "§9Gestionar miembros", "§7Agrega o quita jugadores que pueden acceder."));

        // Cambiar nombre
        menu.setItem(13, crearItem(Material.NAME_TAG, "§dCambiar nombre", "§7Asigna un nuevo nombre a la protección."));

        // Ver información
        menu.setItem(14, crearItem(Material.BOOK, "§bVer información", "§7Ubicación, miembros, flags activas, etc."));

        menu.setItem(15, crearItem(Material.MAP, "§aMostrar visualmente", "§7Muestra el área que cubre tu protección."));


        // Eliminar con confirmación
        menu.setItem(16, crearItem(Material.RED_WOOL, "§cEliminar protección", "§7Abre un menú para confirmar la eliminación."));

        jugador.openInventory(menu);
    }

    private static ItemStack crearItem(Material material, String nombre, String... loreLineas) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(nombre);
        meta.setLore(java.util.Arrays.asList(loreLineas));
        item.setItemMeta(meta);
        return item;
    }
}

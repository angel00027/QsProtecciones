package mp.quesito.qsProtecciones.menus.players;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuConfirmacionEliminar {

    private static final String TITULO = ChatColor.RED + "Confirmar eliminación";

    public static Inventory getMenuConfirmacion() {
        Inventory inv = Bukkit.createInventory(null, 9, TITULO);

        // Botón Confirmar (Green Wool)
        ItemStack confirmar = new ItemStack(Material.GREEN_WOOL);
        ItemMeta confirmarMeta = confirmar.getItemMeta();
        confirmarMeta.setDisplayName(ChatColor.GREEN + "Confirmar eliminación");
        confirmar.setItemMeta(confirmarMeta);

        // Botón Cancelar (Red Wool)
        ItemStack cancelar = new ItemStack(Material.RED_WOOL);
        ItemMeta cancelarMeta = cancelar.getItemMeta();
        cancelarMeta.setDisplayName(ChatColor.RED + "Cancelar eliminación");
        cancelar.setItemMeta(cancelarMeta);

        // Colocar botones en el inventario
        inv.setItem(3, confirmar);
        inv.setItem(5, cancelar);

        return inv;
    }
}

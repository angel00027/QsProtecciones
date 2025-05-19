package mp.quesito.qsProtecciones.menus.players;

import dev.espi.protectionstones.PSRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public class MenuMiembrosRegion {

    public static void abrirMenu(Player jugador, PSRegion region) {
        Inventory menu = Bukkit.createInventory(null, 27, ChatColor.AQUA + "Miembros de la Región");

        // Botón para añadir nuevo miembro
        ItemStack agregar = new ItemStack(Material.LIME_WOOL);
        var agregarMeta = agregar.getItemMeta();
        agregarMeta.setDisplayName(ChatColor.GREEN + "Agregar nuevo miembro");
        agregar.setItemMeta(agregarMeta);
        menu.setItem(0, agregar);

        // Botón para regresar al menú anterior
        ItemStack regresar = new ItemStack(Material.ARROW);
        ItemMeta regresarMeta = regresar.getItemMeta();
        regresarMeta.setDisplayName(ChatColor.YELLOW + "Regresar");
        regresar.setItemMeta(regresarMeta);
        menu.setItem(26, regresar);

        List<UUID> miembros = region.getMembers(); // Obtenemos los miembros como UUID
        int slot = 9;
        for (UUID uuid : miembros) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            String nombre = offlinePlayer.getName();
            if (nombre == null) nombre = uuid.toString().substring(0, 8); // Fallback si el nombre es null

            ItemStack cabeza = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) cabeza.getItemMeta();
            meta.setOwningPlayer(offlinePlayer);
            meta.setDisplayName(ChatColor.RED + "Eliminar: " + nombre);
            cabeza.setItemMeta(meta);
            menu.setItem(slot++, cabeza);
        }

        jugador.openInventory(menu);
    }
}

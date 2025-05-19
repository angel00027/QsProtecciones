package mp.quesito.qsProtecciones.menus.owners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MenuFlagsAdmin {
    private static final Map<UUID, String> regionAbiertaPorJugador = new HashMap<>();
    private static final Map<Integer, StateFlag> flagSlots = new HashMap<>();
    private static final Map<StateFlag, Material> flagToMaterial = new HashMap<>();
    private static final Map<StateFlag, String> flagDescriptions = new HashMap<>();

    static {
        flagSlots.put(10, Flags.PVP);
        flagSlots.put(11, Flags.TNT);
        flagSlots.put(12, Flags.MOB_DAMAGE);

        flagSlots.put(14, Flags.CREEPER_EXPLOSION);
        flagSlots.put(15, Flags.WITHER_DAMAGE);
        flagSlots.put(16, Flags.MOB_SPAWNING);

        flagSlots.put(28, Flags.DAMAGE_ANIMALS);
        flagSlots.put(29, Flags.GHAST_FIREBALL);
        flagSlots.put(30, Flags.ITEM_DROP);

        flagSlots.put(32, Flags.ITEM_PICKUP);


        flagToMaterial.put(Flags.PVP, Material.DIAMOND_SWORD);
        flagToMaterial.put(Flags.TNT, Material.TNT);
        flagToMaterial.put(Flags.MOB_DAMAGE, Material.ZOMBIE_SPAWN_EGG);
        flagToMaterial.put(Flags.CREEPER_EXPLOSION, Material.CREEPER_HEAD);
        flagToMaterial.put(Flags.WITHER_DAMAGE, Material.WITHER_SKELETON_SKULL);
        flagToMaterial.put(Flags.MOB_SPAWNING, Material.SPAWNER);
        flagToMaterial.put(Flags.DAMAGE_ANIMALS, Material.LEAD);
        flagToMaterial.put(Flags.GHAST_FIREBALL, Material.FIRE_CHARGE);
        flagToMaterial.put(Flags.ITEM_DROP, Material.DROPPER);
        flagToMaterial.put(Flags.ITEM_PICKUP, Material.HOPPER);

        flagDescriptions.put(Flags.PVP, "Permite que los jugadores se enfrenten entre sí.");
        flagDescriptions.put(Flags.TNT, "Permite explosiones con TNT.");
        flagDescriptions.put(Flags.MOB_DAMAGE, "Permite que los mobs dañen a los jugadores.");
        flagDescriptions.put(Flags.CREEPER_EXPLOSION, "Permite que los creepers causen daño.");
        flagDescriptions.put(Flags.WITHER_DAMAGE, "Permite que el Wither cause daño.");
        flagDescriptions.put(Flags.MOB_SPAWNING, "Permite el spawn natural de mobs.");
        flagDescriptions.put(Flags.DAMAGE_ANIMALS, "Permite dañar animales pasivos.");
        flagDescriptions.put(Flags.GHAST_FIREBALL, "Permite daño de bolas de fuego del Ghast.");
        flagDescriptions.put(Flags.ITEM_DROP, "Permite dejar caer objetos.");
        flagDescriptions.put(Flags.ITEM_PICKUP, "Permite recoger objetos del suelo.");
    }

    public static void abrir(Player jugador, String regionId) {
        var world = jugador.getWorld();
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));

        if (regionManager == null) {
            jugador.sendMessage(ChatColor.RED + "No se pudo obtener el gestor de regiones.");
            return;
        }

        ProtectedRegion region = regionManager.getRegion(regionId);
        if (region == null) {
            jugador.sendMessage(ChatColor.RED + "No se encontró la región de WorldGuard: " + regionId);
            return;
        }

        Inventory inv = Bukkit.createInventory(null, 54, "§6⚑ Flags de §e" + regionId);

        for (Map.Entry<Integer, StateFlag> entry : flagSlots.entrySet()) {
            int slot = entry.getKey();
            StateFlag flag = entry.getValue();

            var estado = region.getFlag(flag);
            String textoEstado = estado == null ? ChatColor.GRAY + "NINGUNO"
                    : estado == StateFlag.State.ALLOW ? ChatColor.GREEN + "✔ ACTIVADO"
                    : ChatColor.RED + "✘ DESACTIVADO";

            String accion = estado == StateFlag.State.ALLOW
                    ? ChatColor.RED + "→ Click para desactivar"
                    : ChatColor.GREEN + "→ Click para activar";

            Material icono = flagToMaterial.getOrDefault(flag, Material.BOOK);
            ItemStack item = new ItemStack(icono);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(ChatColor.GOLD + "⚐ " + ChatColor.YELLOW + flag.getName().toUpperCase());
            meta.setLore(List.of(
                    ChatColor.GRAY + "✦ " + flagDescriptions.getOrDefault(flag, "Sin descripción."),
                    "",
                    ChatColor.DARK_AQUA + "Estado actual: " + textoEstado,
                    "",
                    accion
            ));

            item.setItemMeta(meta);
            inv.setItem(slot, item);
        }

        // Botón de cerrar
        ItemStack cerrar = new ItemStack(Material.BARRIER);
        ItemMeta cerrarMeta = cerrar.getItemMeta();
        cerrarMeta.setDisplayName(ChatColor.RED + "⛔ Cerrar menú");
        cerrarMeta.setLore(List.of(
                ChatColor.GRAY + "Haz clic para salir del menú."
        ));
        cerrar.setItemMeta(cerrarMeta);
        inv.setItem(49, cerrar);

        regionAbiertaPorJugador.put(jugador.getUniqueId(), regionId);
        jugador.openInventory(inv);
    }


    public static String getRegionAbierta(UUID uuid) {
        return regionAbiertaPorJugador.get(uuid);
    }

    public static Map<Integer, StateFlag> getFlagSlots() {
        return flagSlots;
    }
}

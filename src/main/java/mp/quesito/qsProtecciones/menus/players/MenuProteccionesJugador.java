package mp.quesito.qsProtecciones.menus.players;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import dev.espi.protectionstones.PSPlayer;
import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.mensajes.mensajesjugador;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class MenuProteccionesJugador {
    public static final Map<UUID, PSRegion> regionAbiertaPorJugador = new HashMap<>();

    public static void abrir(Player jugador) {
        List<PSRegion> regiones = getRegionesDelJugador(jugador.getUniqueId());

        if (regiones.isEmpty()) {
            jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "no_protections"));
            return;
        }

        int size = 54; // Tamaño fijo para el inventario
        String rawTitle = mensajesjugador.obtenerPlano(jugador, "menu_tus_protecciones"); // ej: "§a» §bTus Protecciones"
        Component titleComp = LegacyComponentSerializer.legacySection().deserialize(rawTitle);

        Inventory menu = Bukkit.createInventory(null, size, titleComp);


        for (int i = 0; i < regiones.size() && i < size; i++) {
            PSRegion region = regiones.get(i);
            Location loc = region.getHome();
            String nombreRegion = region.getId();

            Block bloqueProteccion = region.getProtectBlock();
            Material materialItem = Material.OAK_SIGN;
            if (bloqueProteccion != null) {
                Material posible = bloqueProteccion.getType();
                if (posible.isItem()) {
                    materialItem = posible;
                }
            }

            ItemStack item = new ItemStack(materialItem);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                String titulo = mensajesjugador.obtenerPlano(jugador, "proteccion_nombre")
                        .replace("{region}", nombreRegion);
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', titulo));


                List<String> lore = new ArrayList<>();

                // Mundo y coordenadas con placeholders
                Map<String, String> coords = Map.of(
                        "world", loc.getWorld().getName(),
                        "x", String.valueOf(loc.getBlockX()),
                        "y", String.valueOf(loc.getBlockY()),
                        "z", String.valueOf(loc.getBlockZ())
                );

                // Aplicar reemplazo + color
                String lineaMundo = mensajesjugador.obtenerPlano(jugador, "lore_world")
                        .replace("{world}", coords.get("world"));
                lore.add(ChatColor.translateAlternateColorCodes('&', lineaMundo));

                String lineaCoords = mensajesjugador.obtenerPlano(jugador, "lore_coordinates")
                        .replace("{x}", coords.get("x"))
                        .replace("{y}", coords.get("y"))
                        .replace("{z}", coords.get("z"));
                lore.add(ChatColor.translateAlternateColorCodes('&', lineaCoords));

                // Mostrar propietarios
                List<UUID> owners = region.getOwners();
                if (!owners.isEmpty()) {
                    lore.add(colorear(mensajesjugador.obtenerPlano(jugador, "owners_title")));
                    for (UUID ownerUUID : owners) {
                        String nombreOwner = Bukkit.getOfflinePlayer(ownerUUID).getName();
                        lore.add("§7- " + nombreOwner);
                    }
                } else {
                    lore.add(colorear(mensajesjugador.obtenerPlano(jugador, "owners_none")));
                }

               // Mostrar miembros
                List<UUID> members = region.getMembers();
                if (!members.isEmpty()) {
                    lore.add(colorear(mensajesjugador.obtenerPlano(jugador, "members_title")));
                    for (UUID memberUUID : members) {
                        String nombreMember = Bukkit.getOfflinePlayer(memberUUID).getName();
                        lore.add("§7- " + nombreMember);
                    }
                } else {
                    lore.add(colorear(mensajesjugador.obtenerPlano(jugador, "members_none")));
                }

                // Instrucción para clic
                lore.add(colorear(mensajesjugador.obtenerPlano(jugador, "click_instruction")));


                meta.setLore(lore);
                item.setItemMeta(meta);
            }

            menu.setItem(i, item);
        }

        jugador.openInventory(menu);
    }

    public static List<PSRegion> getRegionesDelJugador(UUID uuid) {
        Player jugador = Bukkit.getPlayer(uuid);
        if (jugador == null) return Collections.emptyList();

        PSPlayer psJugador = PSPlayer.fromPlayer(jugador);
        if (psJugador == null) return Collections.emptyList();

        List<PSRegion> regiones = new ArrayList<>();
        for (World mundo : Bukkit.getWorlds()) {
            regiones.addAll(psJugador.getPSRegions(mundo, true));
        }

        return regiones;
    }

    public static boolean estaDentroDeRegion(Location bukkitLoc) {
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer()
                .get(BukkitAdapter.adapt(bukkitLoc.getWorld()));
        if (regionManager == null) return false;

        ApplicableRegionSet regionSet = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(bukkitLoc));
        return !regionSet.getRegions().isEmpty();
    }

    public static void mostrarProteccionSiEsta(Player jugador) {
        if (estaDentroDeRegion(jugador.getLocation())) {
            jugador.performCommand("ps view");
            jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "showing_protection"));
            jugador.closeInventory();
        } else {
            jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "not_in_protection"));
        }
    }
    private static String colorear(String texto) {
        return ChatColor.translateAlternateColorCodes('&', texto);
    }

}

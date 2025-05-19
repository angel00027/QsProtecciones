package mp.quesito.qsProtecciones.menus.owners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import dev.espi.protectionstones.PSPlayer;
import dev.espi.protectionstones.PSRegion;
import dev.espi.protectionstones.ProtectionStones;

import mp.quesito.qsProtecciones.listeners.owners.RegionActivityTracker;
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


public class MenuAdmin {

    private static List<PSRegion> cachedRegions = new ArrayList<>();
    public static final Map<UUID, PSRegion> regionAbiertaPorJugador = new HashMap<>();

    public static void abrir(Player jugador) {
        // Actualizamos la lista de regiones (por ejemplo en el mundo "world")
        cachedRegions = obtenerTodasLasProtecciones();


        // ✅ Validar si hay regiones antes de continuar
        if (cachedRegions.isEmpty()) {
            jugador.sendMessage(mensajesjugador.obtenerComponent(jugador, "no_hay_regiones"));
            return;
        }

        int size = 54; // Inventario de tamaño fijo (6 filas)
        String rawTitle = mensajesjugador.obtenerPlano(jugador, "menu_protecciones"); // ej: "§a» §bTus Protecciones"
        Component titleComp = LegacyComponentSerializer.legacySection().deserialize(rawTitle);

        Inventory menu = Bukkit.createInventory(null, size, titleComp);

        for (int i = 0; i < cachedRegions.size() && i < size; i++) {
            PSRegion region = cachedRegions.get(i);
            Location loc = region.getHome();
            String nombre = region.getId();

            Block bloqueProteccion = region.getProtectBlock();
            Material materialItem = Material.OAK_SIGN; // Valor por defecto

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
                        .replace("{region}", nombre);
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', titulo));

                List<String> lore = new ArrayList<>();

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

                Long lastActivity = RegionActivityTracker.getUltimaActividad(region.getId());
                String actividad = (lastActivity == null)
                        ? mensajesjugador.obtenerPlano(jugador, "actividad_nunca")
                        : RegionActivityTracker.calcularTiempoDesconectado(lastActivity);
                String actividadTexto = mensajesjugador.obtenerPlano(jugador, "ultimas_actividad")
                        .replace("{actividad}", actividad);
                lore.add(colorear(actividadTexto));

                // Instrucción para clic
                lore.add(colorear(mensajesjugador.obtenerPlano(jugador, "click_instruction")));

                meta.setLore(lore);
                item.setItemMeta(meta);
            }

            menu.setItem(i, item);
        }

        // ✅ Ahora sí abrimos el inventario porque hay regiones
        jugador.openInventory(menu);
    }


    /**
     * Devuelve todas las regiones de ProtectionStones en un mundo como PSRegion
     */
    private static List<PSRegion> obtenerTodasLasProtecciones() {
        List<PSRegion> resultado = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionManager regions = container.get(BukkitAdapter.adapt(world));
            if (regions == null) continue;

            for (ProtectedRegion region : regions.getRegions().values()) {
                if (region.getId().startsWith("ps")) {
                    List<PSRegion> psRegions = ProtectionStones.getPSRegions(world, region.getId());
                    if (psRegions != null && !psRegions.isEmpty()) {
                        resultado.addAll(psRegions);
                    }
                }
            }
        }

        return resultado;
    }


    public static PSRegion getRegionEnSlot(int slot) {
        if (slot < 0 || slot >= cachedRegions.size()) return null;
        return cachedRegions.get(slot);
    }

    public static List<PSRegion> getCachedRegions() {
        return obtenerTodasLasProtecciones();
        // o recorrer todos los mundos si quieres todas
    }

    private static String calcularTiempoDesconectado(long timestamp) {
        long diffMillis = System.currentTimeMillis() - timestamp;

        long seconds = diffMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) return days + "d";
        if (hours > 0) return hours + "h";
        if (minutes > 0) return minutes + "min";
        return seconds + "s";
    }
    private static String colorear(String texto) {
        return ChatColor.translateAlternateColorCodes('&', texto);
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

}
package mp.quesito.qsProtecciones.listeners.owners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class ActividadRegionListener implements Listener {

    private void registrarActividadEnRegiones(UUID jugadorUUID, ApplicableRegionSet regiones) {
        for (ProtectedRegion region : regiones) {
            if (region.getId().startsWith("ps") &&
                    (region.getOwners().contains(jugadorUUID) || region.getMembers().contains(jugadorUUID))) {
                RegionActivityTracker.registrarActividad(region.getId());
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;

        RegionManager regionManager = WorldGuard.getInstance().getPlatform()
                .getRegionContainer()
                .get(BukkitAdapter.adapt(event.getPlayer().getWorld()));
        if (regionManager == null) return;

        ApplicableRegionSet regiones = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(event.getTo()));
        registrarActividadEnRegiones(event.getPlayer().getUniqueId(), regiones);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        RegionManager regionManager = WorldGuard.getInstance().getPlatform()
                .getRegionContainer()
                .get(new BukkitWorld(event.getBlock().getWorld()));
        if (regionManager == null) return;

        ApplicableRegionSet regiones = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(event.getBlock().getLocation()));
        registrarActividadEnRegiones(event.getPlayer().getUniqueId(), regiones);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        RegionManager regionManager = WorldGuard.getInstance().getPlatform()
                .getRegionContainer()
                .get(new BukkitWorld(event.getBlock().getWorld()));
        if (regionManager == null) return;

        ApplicableRegionSet regiones = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(event.getBlock().getLocation()));
        registrarActividadEnRegiones(event.getPlayer().getUniqueId(), regiones);
    }
}

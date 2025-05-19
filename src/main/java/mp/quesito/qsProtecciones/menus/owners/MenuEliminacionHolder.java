package mp.quesito.qsProtecciones.menus.owners;

import dev.espi.protectionstones.PSProtectBlock;
import dev.espi.protectionstones.PSRegion;
import dev.espi.protectionstones.ProtectionStones;
import mp.quesito.qsProtecciones.managers.owners.AbstractMenuHolder;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuEliminacionHolder extends AbstractMenuHolder {

    private final PSRegion region;

    public MenuEliminacionHolder(PSRegion region) {
        this.region = region;
    }

    public static void abrir(Player jugador, PSRegion region) {
        Inventory inv = Bukkit.createInventory(new MenuEliminacionHolder(region), 9,
                mensajesnormales.obtener("menus.confirmacion_eliminacion.titulo"));

        // Botón confirmar
        ItemStack confirmar = new ItemStack(Material.GREEN_WOOL);
        ItemMeta confirmarMeta = confirmar.getItemMeta();
        confirmarMeta.setDisplayName(mensajesnormales.obtener("menus.confirmacion_eliminacion.confirmar"));
        confirmar.setItemMeta(confirmarMeta);

        // Botón cancelar
        ItemStack cancelar = new ItemStack(Material.RED_WOOL);
        ItemMeta cancelarMeta = cancelar.getItemMeta();
        cancelarMeta.setDisplayName(mensajesnormales.obtener("menus.confirmacion_eliminacion.cancelar"));
        cancelar.setItemMeta(cancelarMeta);

        inv.setItem(3, confirmar);
        inv.setItem(5, cancelar);

        jugador.openInventory(inv);
    }

    @Override
    public void manejarClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player jugador)) return;

        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        Material tipo = item.getType();

        if (tipo == Material.GREEN_WOOL) {
            // Eliminar la región sin confirmación adicional
            PSProtectBlock tipoBloque = region.getTypeOptions();

            if (tipoBloque != null) {
                ItemStack bloque = ProtectionStones.createProtectBlockItem(tipoBloque);
                jugador.getInventory().addItem(bloque);
            }

            region.deleteRegion(true, jugador);
            jugador.sendMessage(mensajesnormales.obtenerComponent("region_eliminada_exitos"));
            jugador.playSound(jugador.getLocation(), Sound.ENTITY_ZOMBIE_DEATH, 1, 1);
            jugador.closeInventory();

        } else if (tipo == Material.RED_WOOL) {
            jugador.sendMessage(mensajesnormales.obtenerComponent("eliminacion_canceladas"));
            jugador.playSound(jugador.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 0.5f);
            jugador.closeInventory();
        }
    }
}

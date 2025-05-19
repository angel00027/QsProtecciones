package mp.quesito.qsProtecciones.managers.owners;


import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class AbstractMenuHolder implements InventoryHolder {
    @Override
    public Inventory getInventory() {
        return null; // No se usa directamente
    }

    public abstract void manejarClick(InventoryClickEvent event);
}

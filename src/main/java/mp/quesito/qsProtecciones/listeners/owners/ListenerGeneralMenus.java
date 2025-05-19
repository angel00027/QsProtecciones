package mp.quesito.qsProtecciones.listeners.owners;
import mp.quesito.qsProtecciones.managers.owners.AbstractMenuHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ListenerGeneralMenus implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof AbstractMenuHolder holder)) return;

        holder.manejarClick(event);
    }
}

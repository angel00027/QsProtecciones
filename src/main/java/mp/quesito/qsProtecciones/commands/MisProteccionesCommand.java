package mp.quesito.qsProtecciones.commands;

import mp.quesito.qsProtecciones.menuprincipal.MenuPrincipal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MisProteccionesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player jugador)) {
            sender.sendMessage("Solo los jugadores pueden usar este comando.");
            return true;
        }

        MenuPrincipal.abrir(jugador);
        return true;

    }
}

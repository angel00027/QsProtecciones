package mp.quesito.qsProtecciones.commands;

import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ProteccionqsCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public ProteccionqsCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("proteccionqs.reload")) {
                sender.sendMessage(ChatColor.RED + "No tienes permiso para usar este comando.");
                return true;
            }

            plugin.reloadConfig();

            String idioma = plugin.getConfig().getString("lang", "es");
            mensajesnormales.recargar(plugin, idioma);

            sender.sendMessage(ChatColor.GREEN + "ProteccionQS recargado correctamente.");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "Uso: /proteccionqs reload");
        return true;
    }
}
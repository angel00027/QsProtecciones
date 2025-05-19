package mp.quesito.qsProtecciones.utils.owners;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LogHelper {

    private static JavaPlugin plugin;
    private static File logFile;

    public static void setup(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        logFile = new File(plugin.getDataFolder(), "eliminacion_regiones.log");

        if (!logFile.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                logFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("No se pudo crear el archivo de log de eliminación.");
                e.printStackTrace();
            }
        }
    }

    public static void log(String mensaje) {
        String timestamp = LocalDateTime.now().toString();
        String texto = "[" + timestamp + "] " + mensaje;

        // Log en consola
        plugin.getLogger().info(texto);

        // Append en archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(texto);
            writer.newLine();
        } catch (IOException e) {
            plugin.getLogger().severe("Error al escribir en archivo de log.");
            e.printStackTrace();
        }
    }
}


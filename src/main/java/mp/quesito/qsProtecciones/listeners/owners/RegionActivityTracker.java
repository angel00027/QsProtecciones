package mp.quesito.qsProtecciones.listeners.owners;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegionActivityTracker {

    private static final Map<String, Long> actividad = new HashMap<>();
    private static File archivo;
    private static YamlConfiguration config;
    private static Plugin plugin;

    // Llama a este método al iniciar el plugin para preparar todo
    public static void inicializar(Plugin pl) {
        plugin = pl;
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();  // Crea carpeta si no existe
        }

        archivo = new File(dataFolder, "actividad_regiones.yml");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();  // Crea el archivo si no existe
            } catch (IOException e) {
                Bukkit.getLogger().warning("[ProtecionQS] No se pudo crear el archivo de actividad de regiones.");
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(archivo);

        cargar(); // Carga datos existentes (si los hay)
    }

    public static void registrarActividad(String regionId) {
        long ahora = System.currentTimeMillis();
        actividad.put(regionId, ahora);
        config.set(regionId, ahora);
        guardar();
    }

    public static Long getUltimaActividad(String regionId) {
        return actividad.get(regionId);
    }

    public static void cargar() {
        if (!archivo.exists()) return;

        for (String key : config.getKeys(false)) {
            long time = config.getLong(key);
            actividad.put(key, time);
        }
    }

    public static void guardarDatos() {
        guardar();
    }

    private static void guardar() {
        try {
            config.save(archivo);
        } catch (IOException e) {
            Bukkit.getLogger().warning("[ProtecionQS] No se pudo guardar la actividad de regiones.");
            e.printStackTrace();
        }
    }

    public static String calcularTiempoDesconectado(long millisUltimaActividad) {
        long diferencia = System.currentTimeMillis() - millisUltimaActividad;

        long dias = diferencia / (1000 * 60 * 60 * 24);
        long horas = (diferencia / (1000 * 60 * 60)) % 24;
        long minutos = (diferencia / (1000 * 60)) % 60;

        if (dias > 0) return dias + "d " + horas + "h";
        else if (horas > 0) return horas + "h " + minutos + "min";
        else return minutos + "min";
    }
}

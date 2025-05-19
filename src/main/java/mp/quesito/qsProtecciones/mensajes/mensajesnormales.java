package mp.quesito.qsProtecciones.mensajes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mensajesnormales {
    private static final Map<String, YamlConfiguration> idiomas = new HashMap<>();
    private static String idiomaActual = "es";
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static void cargar(JavaPlugin plugin, String lang) {
        idiomaActual = lang;

        File file = new File(plugin.getDataFolder(), "lang/" + lang + ".yml");
        if (!file.exists()) {
            plugin.saveResource("lang/" + lang + ".yml", false);
        }

        idiomas.put(lang, YamlConfiguration.loadConfiguration(file));
    }

    // Obtiene mensaje con MiniMessage y lo parsea a Component
    public static Component obtenerComponent(String clave) {
        YamlConfiguration config = idiomas.get(idiomaActual);
        if (config == null) return miniMessage.deserialize("<red>[LANG: NO CARGADO]</red>");

        String msg = config.getString(clave);
        if (msg == null) return miniMessage.deserialize("<red>[MSG FALTANTE: " + clave + "]</red>");

        return miniMessage.deserialize(msg);
    }

    // Obtiene mensaje con MiniMessage + placeholders y lo parsea a Component
    public static Component obtenerComponent(String clave, Map<String, String> placeholders) {
        String mensaje = idiomas.get(idiomaActual).getString(clave, "");
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            mensaje = mensaje.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return miniMessage.deserialize(mensaje);
    }

    // Obtiene mensaje con & y lo transforma en String con colores (§) para usar en setDisplayName o lore
    public static String obtener(String clave) {
        YamlConfiguration config = idiomas.get(idiomaActual);
        if (config == null) return "§c[LANG: NO CARGADO]";
        String msg = config.getString(clave);
        return msg != null ? ChatColor.translateAlternateColorCodes('&', msg) : "§c[MSG FALTANTE: " + clave + "]";
    }

    // Obtiene lista de mensajes con & y la transforma para lore o listas con colores (§)
    public static List<String> obtenerListaConColores(String clave) {
        YamlConfiguration config = idiomas.get(idiomaActual);
        if (config == null) return List.of("§c[LANG: NO CARGADO]");

        List<String> lista = config.getStringList(clave);
        List<String> listaColoreada = new ArrayList<>();
        for (String linea : lista) {
            listaColoreada.add(ChatColor.translateAlternateColorCodes('&', linea));
        }
        if (listaColoreada.isEmpty()) {
            listaColoreada.add("§c[LISTA VACÍA: " + clave + "]");
        }
        return listaColoreada;
    }

    public static void recargar(JavaPlugin plugin, String nuevoIdioma) {
        idiomaActual = nuevoIdioma;
        idiomas.remove(nuevoIdioma);
        cargar(plugin, nuevoIdioma);
    }

    public static String getIdiomaActual() {
        return idiomaActual;
    }

    public static Map<String, YamlConfiguration> getIdiomas() {
        return idiomas;
    }

    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }
}

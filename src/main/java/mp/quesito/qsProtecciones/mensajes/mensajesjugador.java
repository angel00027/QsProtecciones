package mp.quesito.qsProtecciones.mensajes;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class mensajesjugador {

    private static final Map<Player, String> idiomasPorJugador = new HashMap<>();

    /**
     * Establece el idioma para un jugador.
     */
    public static void setIdiomaJugador(Player jugador, String idioma) {
        idiomasPorJugador.put(jugador, idioma);
    }

    /**
     * Remueve el idioma personalizado para un jugador (usa idioma global).
     */
    public static void removeIdiomaJugador(Player jugador) {
        idiomasPorJugador.remove(jugador);
    }

    /**
     * Devuelve el componente traducido para el jugador con placeholders.
     */
    public static Component obtenerComponent(Player jugador, String clave, Map<String, String> placeholders) {
        String idioma = getIdiomaJugador(jugador);
        var idiomasMap = mensajesnormales.getIdiomas();  // ahora con getter
        var miniMessage = mensajesnormales.getMiniMessage();

        var config = idiomasMap.get(idioma);
        if (config == null) return miniMessage.deserialize("<red>[LANG: NO CARGADO]</red>");

        String mensaje = config.getString(clave, "");
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            mensaje = mensaje.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return miniMessage.deserialize(mensaje);
    }

    public static String getIdiomaJugador(Player jugador) {
        return idiomasPorJugador.getOrDefault(jugador, mensajesnormales.getIdiomaActual());
    }
    /**
     * Sobrecarga para obtener componente sin placeholders.
     */
    public static Component obtenerComponent(Player jugador, String clave) {
        return obtenerComponent(jugador, clave, Map.of());
    }
    /**
     * Devuelve el mensaje como texto plano (String) para el idioma del jugador.
     */
    public static String obtenerPlano(Player jugador, String clave) {
        String idioma = getIdiomaJugador(jugador);
        YamlConfiguration config = mensajesnormales.getIdiomas().get(idioma);
        if (config == null) return "[LANG: NO CARGADO]";
        String msg = config.getString(clave);
        return msg != null ? msg : "[MSG FALTANTE: " + clave + "]";
    }

}

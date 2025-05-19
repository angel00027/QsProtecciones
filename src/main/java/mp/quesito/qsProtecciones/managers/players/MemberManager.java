package mp.quesito.qsProtecciones.managers.players;

import dev.espi.protectionstones.PSRegion;
import mp.quesito.qsProtecciones.menus.players.MenuMiembrosRegion;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemberManager {

    private static final Map<UUID, PSRegion> regionPorJugador = new HashMap<>();
    private static final Map<UUID, Boolean> esperandoNombre = new HashMap<>();

    public static void setRegion(UUID uuid, PSRegion region) {
        regionPorJugador.put(uuid, region);
    }

    public static PSRegion getRegion(UUID uuid) {
        return regionPorJugador.get(uuid);
    }

    public static void abrirMenu(Player jugador, PSRegion region) {
        setRegion(jugador.getUniqueId(), region);
        MenuMiembrosRegion.abrirMenu(jugador, region);
    }

    public static void setEsperandoNombre(UUID uuid, boolean valor) {
        esperandoNombre.put(uuid, valor);
    }

    public static boolean estaEsperandoNombre(UUID uuid) {
        return esperandoNombre.getOrDefault(uuid, false);
    }
}

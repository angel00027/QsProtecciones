package mp.quesito.qsProtecciones.managers.players;

import dev.espi.protectionstones.PSRegion;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RenameManager {
    private static final Map<UUID, PSRegion> jugadoresRenombrando = new HashMap<>();

    public static void addJugadorRenombrando(UUID jugadorUUID, PSRegion region) {
        jugadoresRenombrando.put(jugadorUUID, region);
    }

    public static PSRegion getRegionRenombrando(UUID jugadorUUID) {
        return jugadoresRenombrando.get(jugadorUUID);
    }

    public static void removeJugadorRenombrando(UUID jugadorUUID) {
        jugadoresRenombrando.remove(jugadorUUID);
    }

    public static boolean estaRenombrando(UUID jugadorUUID) {
        return jugadoresRenombrando.containsKey(jugadorUUID);
    }
}

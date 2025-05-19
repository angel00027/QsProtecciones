package mp.quesito.qsProtecciones.managers.players;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConfirmacionManager {

    private static final Map<UUID, String> confirmacionRegionMap = new HashMap<>();

    public static void put(UUID jugadorUUID, String regionId) {
        confirmacionRegionMap.put(jugadorUUID, regionId);
    }

    public static String get(UUID jugadorUUID) {
        return confirmacionRegionMap.get(jugadorUUID);
    }

    public static void remove(UUID jugadorUUID) {
        confirmacionRegionMap.remove(jugadorUUID);
    }

    public static boolean contains(UUID jugadorUUID) {
        return confirmacionRegionMap.containsKey(jugadorUUID);
    }
}

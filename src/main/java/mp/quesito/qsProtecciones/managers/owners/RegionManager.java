package mp.quesito.qsProtecciones.managers.owners;

import dev.espi.protectionstones.PSRegion;

import java.util.HashMap;
import java.util.Map;

public class RegionManager {

    private final Map<String, PSRegion> regiones = new HashMap<>();

    // Añade o actualiza una región
    public void addRegion(PSRegion region) {
        regiones.put(region.getId(), region);
    }

    // Obtiene una región por su ID
    public PSRegion getRegionById(String id) {
        return regiones.get(id);
    }

    // Remueve una región si es necesario
    public void removeRegion(String id) {
        regiones.remove(id);
    }
}


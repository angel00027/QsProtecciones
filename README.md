# 🛡️ QSProtecciones

Sistema avanzado de gestión de protecciones para servidores Minecraft con soporte para **ProtectionStones** y **WorldGuard**.  
Este plugin proporciona una interfaz gráfica amigable para administradores y jugadores, facilitando el manejo, visualización y administración de regiones protegidas en el mundo.

---
## ✨ Características

- 📦 Interfaz gráfica (GUI) amigable.
- 👥 Visualización y gestión de propietarios y miembros de las regiones.
- 🧭 Teletransporte a protecciones.
- 🗑️ Eliminar regiones directamente desde el menú.
- ⚙️ Acciones administrativas adicionales (flags, información técnica, etc.).
- 📜 Localización personalizable (mensajes en archivos YAML).
- ⌛ Sistema de inactividad (actividad de la región basada en conexión de los usuarios).
- 🧠 Carga dinámica de regiones y actualizaciones en tiempo real.

---
## 🚀 Instalación

1. Descarga el archivo `.jar`
2. Colócalo en la carpeta `plugins/` de tu servidor.
3. Reinicia el servidor para cargar el plugin.
---

## 📷 Capturas
###  Menu principal 
Visualiza el acceso inicial del administrador a las funcionalidades de gestión de regiones.
![image](https://github.com/user-attachments/assets/3ee3e8ac-04a9-44a7-a9ce-d673b96e77ef)
### Menu de protecciones
Muestra todas las regiones registradas en el mundo, con información como propietarios, coordenadas y actividad reciente.

![image](https://github.com/user-attachments/assets/156a47db-3a38-4038-849f-0d57e6586ece)

### Menu de seleccion
Permite elegir una opcion específica para acceder a más acciones sobre ella.
![image](https://github.com/user-attachments/assets/13e09f42-9063-4d67-8235-88e3eca6ce13)

### Menu de acciones
Ofrece herramientas para ver información detallada de la región, teleportarse a ella y realizar otras acciones administrativas como modificar flags, ver o editar miembros, eliminar la región, entre otras funciones útiles para su gestión.

![image](https://github.com/user-attachments/assets/39e1792b-6a2a-40b7-aafa-9505b3f3eed2)

---

## 🔧 Requisitos

- Minecraft 1.20+ (adaptable a otras versiones)
- [WorldGuard](https://enginehub.org/worldguard)
- [ProtectionStones](https://www.spigotmc.org/resources/protectionstones-updated.61797/)
- Java 17+
- Plugin loader compatible (Spigot, Paper, Purpur, etc.)

---

## 🧪 Comandos

| Comando                                 | Descripción                                     |
|-----------------------------------------|-------------------------------------------------|
| `/misproteciones` o `/psmenu`           | Abre el menú principal        |
| `/pqs reload` o `/proteccionqs reload`  | Recarga los archivos de  mensajes               |

---

## 🧱 Archivos de configuración

El archivo `mensajes.yml` permite personalizar completamente los textos visibles en menús y mensajes enviados al jugador.

Ejemplo:

```yaml
# ********************************************************
# *                      QsProtecciones                  *
# *               Mensajes del plugin en español          *
# ********************************************************

# ================================================================
# Instrucciones sobre los formatos de mensajes
# ================================================================
#
# 1. Mensajes en formato MiniMessage:
#    - Estos mensajes utilizan etiquetas como <red>, <green>, <yellow>, etc.
#    - SOLO pueden usarse estas etiquetas para definir colores y estilos.
#    - Ejemplo válido:
#        <red>No eres el propietario de esta protección.</red>
#    - NO se debe usar el formato con el símbolo & en estos mensajes.
#    - Por lo tanto, NO es correcto usar:
#        &cNo eres el propietario de esta protección.
#
# 2. Mensajes en formato Legacy (con símbolo &):
#    - Estos mensajes utilizan códigos de color con el símbolo & seguido de una letra o número.
#    - Ejemplo válido:
#        &cNo eres el propietario de esta protección.
#    - NO se debe usar etiquetas MiniMessage (<red>...</red>) en estos mensajes.
#    - Por lo tanto, NO es correcto usar:
#        <red>No eres el propietario de esta protección.</red>
#
# Resumen:
# - Cada mensaje debe mantenerse en su formato correspondiente.
# - No mezclar etiquetas MiniMessage con códigos Legacy (&).
# - Así se evita que los mensajes no se muestren correctamente en el plugin.
#
# =================================================================

# ==========================
# Mensajes en formato MiniMessage
# ==========================

no_tp: "<red>Esta protección no tiene punto de teletransporte.</red>"
tp_success: "<green>Teletransportado a: <white>{region}</white></green>"
config_open: "<yellow>Configurando protección: <green>{region}</green></yellow>"
no_permissions: "<red>No tienes permisos para hacer esto.</red>"

# ==========================
# Mensajes en formato Legacy (& o §)
# ==========================

menu_protecciones: "§a» §bTus Protecciones"
owners_title: "&7Propietarios:"
owners_none: "&cSin propietarios"
click_instruction: "&7Haz clic para administrar esta protección"
actividad_nunca: "&cNunca ha habido actividad"
ultimas_actividad: "&7Última actividad: &f{actividad}"
```

---

## 📚 Créditos

Desarrollado por [@angel00027](https://github.com/angel00027).  
Basado en los plugins y APIs de:
- [WorldGuard](https://github.com/EngineHub/WorldGuard)
- [ProtectionStones](https://github.com/espidev/ProtectionStones)

---

## 📝 Licencia

Este proyecto está licenciado bajo los términos de una licencia personalizada. Puedes usar y distribuir este plugin libremente, siempre y cuando se otorgue la debida atribución al autor original. **No está permitido modificar ni redistribuir versiones alteradas** sin autorización explícita.

Para cualquier uso distinto, por favor contacta con el autor.


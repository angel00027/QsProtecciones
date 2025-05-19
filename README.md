# QsProtecciones

Sistema avanzado de gestión de protecciones para servidores Minecraft con soporte para **ProtectionStones** y **WorldGuard**.  
Este plugin proporciona una interfaz gráfica amigable para administradores y jugadores, facilitando el manejo, visualización y administración de regiones protegidas en el mundo.

---

## ✨ Características

- 📦 Listado de todas las regiones de ProtectionStones en el mundo.
- 📍 Visualización de ubicación, propietarios, miembros y actividad reciente.
- 🧭 Interfaz GUI personalizable para abrir menús de administración y de jugadores.
- 🛠️ Compatible con WorldGuard y ProtectionStones.
- ⏳ Seguimiento de actividad (última conexión) de cada región.
- 📜 Textos completamente configurables mediante archivos de mensajes.

---

## 📷 Capturas

*(Puedes agregar imágenes del menú principal, submenús y mensajes personalizados.)*

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

Este proyecto está bajo la licencia MIT. Puedes usarlo, modificarlo y distribuirlo libremente con atribución adecuada.

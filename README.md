# QsProtecciones

Sistema avanzado de gestión de protecciones para servidores Minecraft con soporte para **ProtectionStones** y **WorldGuard**.  
Este plugin proporciona una interfaz gráfica amigable para administradores y jugadores, facilitando el manejo, visualización y administración de regiones protegidas en el mundo.

---

## ✨ Características

- 📦 Listado de todas las regiones de ProtectionStones en el mundo.
- 📍 Visualización de ubicación, propietarios, miembros y actividad reciente.
- 🧭 Interfaz GUI personalizable para abrir menús de administración.
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
| `/misproteciones` o `/psmenu`           | Abre el menú de regiones administrables         |
| `/pqs reload` o `/proteccionqs reload`  | Abre el menú de regiones administrables         |

---

## 🧱 Archivos de configuración

El archivo `mensajes.yml` permite personalizar completamente los textos visibles en menús y mensajes enviados al jugador.

Ejemplo:

```yaml
menu_protecciones: "§a» §bTus Protecciones"
owners_title: "&7Propietarios:"
owners_none: "&cSin propietarios"
click_instruction: "&7Haz clic para administrar esta protección"
actividad_nunca: "&cNunca ha habido actividad"
ultimas_actividad: "&7Última actividad: &f{actividad}"

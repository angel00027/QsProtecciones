qsProtecciones

qsProtecciones es un plugin de Minecraft desarrollado para facilitar la administración de regiones protegidas usando ProtectionStones y WorldGuard. Proporciona un menú interactivo amigable tanto para jugadores como para administradores, permitiendo ver, gestionar y controlar regiones de manera visual.

Características principales

✫ Menú GUI para mostrar todas las protecciones registradas.

✫ Información detallada: ID de la región, mundo, coordenadas, dueños, miembros y última actividad.

✫ Acciones rápidas desde el menú (ver región, abrir submenús, etc).

✫ Totalmente configurable: mensajes personalizados mediante archivos de configuración.

✫ Diseñado para ser ligero, intuitivo y funcional.

Requisitos

Servidor Spigot/Paper 1.19+

WorldGuard

ProtectionStones

Instalación

Coloca el archivo qsProtecciones.jar en la carpeta /plugins/ de tu servidor.

Asegúrate de tener instalados y configurados correctamente WorldGuard y ProtectionStones.

Reinicia o recarga el servidor.

Comandos

Actualmente, las interacciones se realizan principalmente a través del menú. Puedes abrir el menú de administración con:

/protecciones admin

Asegúrate de tener los permisos adecuados si tu plugin los implementa.

Archivos de configuración

El plugin permite personalizar los textos y mensajes mediante un archivo de mensajes. Por ejemplo:

menu_protecciones: "&a» &bTus Protecciones"
proteccion_nombre: "&bProtección: &e{region}"
owners_title: "&7Dueños:"
owners_none: "&c(Sin dueño)"
actividad_nunca: "Nunca conectó"
ultimas_actividad: "&7Última actividad: {actividad}"
click_instruction: "&eHaz clic para ver opciones"
not_in_protection: "&cNo estás dentro de una protección."

Capturas de pantalla

Próximamente...

Contribuciones

Las contribuciones son bienvenidas. Si deseas reportar errores o sugerir mejoras, puedes abrir un issue o pull request.

Licencia

Este proyecto está licenciado bajo MIT License. Puedes ver el archivo LICENSE para más detalles.

package mp.quesito.qsProtecciones;

import mp.quesito.qsProtecciones.commands.MisProteccionesCommand;
import mp.quesito.qsProtecciones.commands.ProteccionqsCommand;
import mp.quesito.qsProtecciones.listeners.owners.*;
import mp.quesito.qsProtecciones.listeners.players.*;
import mp.quesito.qsProtecciones.listeners.players.RegionListener;
import mp.quesito.qsProtecciones.mensajes.mensajesnormales;
import mp.quesito.qsProtecciones.menuprincipal.MenuPrincipalListener;
import mp.quesito.qsProtecciones.utils.owners.LogHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;

public final class QsProtecciones extends JavaPlugin {

    private RegionListener regionListener;
    private static QsProtecciones instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        String idiomaElegido = getConfig().getString("idioma", "es");
        RegionActivityTracker.inicializar(this);
        RegionActivityTracker.cargar();
        LogHelper.setup(this);

        // Cargar el idioma en Lang
        mensajesnormales.cargar(this, idiomaElegido);
        Component mensajeHabilitado  = mensajesnormales.obtenerComponent("plugin-start");
        getServer().getConsoleSender().sendMessage(mensajeHabilitado );


        //eventos de listeners
        getServer().getPluginManager().registerEvents(new ActividadRegionListener(), this);
        getServer().getPluginManager().registerEvents(new MenuPrincipalListener(), this);
        getServer().getPluginManager().registerEvents(new MenuProteccionesListener(), this);
        getServer().getPluginManager().registerEvents(new MenuSeleccionProteccionListener(), this);
        getServer().getPluginManager().registerEvents(new RegionListener(), this);
        getServer().getPluginManager().registerEvents(new MenuAccionesProteccionListener(), this);
        getServer().getPluginManager().registerEvents(new ConfirmacionEliminarListener(), this);
        getServer().getPluginManager().registerEvents(new RenameRegionListener(), this);
        getServer().getPluginManager().registerEvents(new MenuMiembrosListener(), this);
        getServer().getPluginManager().registerEvents(new AgregarMiembroListener(), this);
        getServer().getPluginManager().registerEvents(new MenuFlagsWGListener(), this);
        //menu de admin
        getServer().getPluginManager().registerEvents(new AdminListener(), this);
        getServer().getPluginManager().registerEvents(new ListenerGeneralMenus(), this);
        getServer().getPluginManager().registerEvents(new AgregarMiembroAdm(), this);
        getServer().getPluginManager().registerEvents(new ListenerFlagsAdm(), this);
        getServer().getPluginManager().registerEvents(new ListenerMiembrosAdm(), this);
        getServer().getPluginManager().registerEvents(new RenameListener(), this);


        //comandos
        this.getCommand("proteccionqs").setExecutor(new ProteccionqsCommand(this));
        this.getCommand("misprotecciones").setExecutor(new MisProteccionesCommand());


        regionListener = new RegionListener();
        getServer().getPluginManager().registerEvents(regionListener, this);
        instance = this;



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Component mensajeHabilitado  = mensajesnormales.obtenerComponent("plugin-stop");
        getServer().getConsoleSender().sendMessage(mensajeHabilitado );

    }
    public static QsProtecciones getInstance() {
        return instance;
    }

}

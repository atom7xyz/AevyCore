package fun.aevy.aevycore;

import fun.aevy.aevycore.commands.AevyCommand;
import fun.aevy.aevycore.struct.elements.Reloadable;
import fun.aevy.aevycore.struct.elements.database.Database;
import fun.aevy.aevycore.struct.elements.database.DatabaseConnection;
import fun.aevy.aevycore.struct.elements.events.EventListener;
import fun.aevy.aevycore.struct.manager.DatabasesManager;
import fun.aevy.aevycore.utils.configuration.elements.ConfigType;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
import fun.aevy.aevycore.utils.configuration.entries.Aevy;
import fun.aevy.aevycore.utils.formatting.Send;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * AevyCore's main class.
 * @since 1.0
 * @author Sorridi
 */
@Getter
public final class AevyCore extends JavaPlugin
{
    private static AevyCore aevyCore;

    private String version, site;

    private PluginManager pluginManager;

    private DatabasesManager    databasesManager;
    private Database            database;
    private boolean             databaseEnabled;

    private CoolConfig  coolConfig;
    private Send        send;

    private List<Reloadable> reloadables;

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        aevyCore = this;

        coolConfig  = new CoolConfig(this, ConfigType.DEFAULT);
        send        = new Send(coolConfig);
        reloadables = new ArrayList<>();

        databaseEnabled = (Boolean) coolConfig.getValue(Aevy.Database.ENABLED);
        
        if (databaseEnabled)
        {
            databasesManager = new DatabasesManager(this);

            DatabaseConnection databaseConnection = new DatabaseConnection(coolConfig);
            databasesManager.addConnection("AevyCore", databaseConnection);

            database = new Database(this, databasesManager, databaseConnection);
            database.debug((Boolean) coolConfig.getValue(Aevy.Database.DEBUG));
        }

        /* Loads all the useful variables. */
        PluginDescriptionFile descriptionFile = getDescription();
        version = descriptionFile.getVersion();
        site    = descriptionFile.getWebsite();

        pluginManager = getServer().getPluginManager();

        new AevyCommand(this, "aevy")
                .setUsage(Aevy.Usages.AEVY)
                .setTabComplete(true)
                .build();

        new EventListener(this);
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic

        if (databaseEnabled)
        {
            /* Closes every database and database connection. */
            databasesManager.shutdown();
        }
    }

    /**
     * Gets the AevyCore instance.
     * @return The instance.
     */
    public static AevyCore getInstance()
    {
        return aevyCore;
    }

    public void callEvent(Event event)
    {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    public void addReloadable(Reloadable reloadable)
    {
        reloadables.add(reloadable);
        reloadReloadable(reloadable);
    }

    public void reloadReloadable(Reloadable reloadable)
    {
        reloadable.reloadVars();
    }

    public void reloadReloadables()
    {
        reloadables.forEach(this::reloadReloadable);
    }

    public void removeReloadable(Reloadable reloadable)
    {
        reloadables.remove(reloadable);
    }
}

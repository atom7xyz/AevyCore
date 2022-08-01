package fun.aevy.aevycore;

import fun.aevy.aevycore.commands.AevyCommand;
import fun.aevy.aevycore.struct.elements.Reloadable;
import fun.aevy.aevycore.struct.elements.database.Database;
import fun.aevy.aevycore.struct.elements.database.DatabaseConnection;
import fun.aevy.aevycore.struct.manager.DatabasesManager;
import fun.aevy.aevycore.utils.configuration.elements.ConfigType;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
import fun.aevy.aevycore.utils.configuration.entries.Aevy;
import fun.aevy.aevycore.utils.formatting.Send;
import lombok.Getter;
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

    private List<Reloadable> canReload;

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        aevyCore = this;

        coolConfig  = new CoolConfig(this, ConfigType.DEFAULT);
        send        = new Send(coolConfig);
        canReload   = new ArrayList<>();

        String path = "permissions";
        coolConfig.add(Aevy.Perms.RELOAD,           path);

        path = "messages";
        coolConfig.add(Aevy.Messages.PREFIX,        path);
        coolConfig.add(Aevy.Messages.NO_PERMS,      path);
        coolConfig.add(Aevy.Messages.NO_PLAYER,     path);
        coolConfig.add(Aevy.Messages.NO_CONSOLE,    path);

        path += ".commands.aevy";
        coolConfig.add(Aevy.Messages.RELOAD,        path);
        coolConfig.add(Aevy.Messages.VERSION,       path);

        path = "messages.usages";
        coolConfig.add(Aevy.Usages.AEVY,            path);

        path = "database";
        coolConfig.add(Aevy.Database.ENABLED,       path);

        databaseEnabled = (Boolean) coolConfig.getValue(Aevy.Database.ENABLED);
        if (databaseEnabled)
        {
            coolConfig.add(Aevy.Database.DATABASE,          path);
            coolConfig.add(Aevy.Database.DRIVER,            path);
            coolConfig.add(Aevy.Database.URL,               path);
            coolConfig.add(Aevy.Database.IP,                path);
            coolConfig.add(Aevy.Database.PORT,              path);
            coolConfig.add(Aevy.Database.USER,              path);
            coolConfig.add(Aevy.Database.PASSWORD,          path);
            coolConfig.add(Aevy.Database.MAX_POOL_SIZE,     path);
            coolConfig.add(Aevy.Database.DEBUG,             path);
            coolConfig.add(Aevy.Database.USE_DEFAULTS,      path);
            coolConfig.add(Aevy.Database.PROPERTIES,        path);

            databasesManager = new DatabasesManager(this);

            DatabaseConnection databaseConnection = new DatabaseConnection(coolConfig);
            databasesManager.addConnection("AevyCore", databaseConnection);

            database = new Database(this, databasesManager, databaseConnection);
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

}

package fun.aevy.aevycore;

import fun.aevy.aevycore.commands.ReloadCommand;
import fun.aevy.aevycore.commands.VersionCommand;
import fun.aevy.aevycore.events.PlayerEvents;
import fun.aevy.aevycore.struct.elements.database.Database;
import fun.aevy.aevycore.struct.elements.database.DatabaseConnection;
import fun.aevy.aevycore.struct.manager.DatabasesManager;
import fun.aevy.aevycore.struct.manager.PlayersManager;
import fun.aevy.aevycore.utils.configuration.Config;
import fun.aevy.aevycore.utils.configuration.ConfigType;
import fun.aevy.aevycore.utils.configuration.entries.AevyCoreEntries;
import fun.aevy.aevycore.utils.configuration.entries.DatabaseEntries;
import fun.aevy.aevycore.utils.configuration.entries.GlobalEntries;
import fun.aevy.aevycore.utils.formatting.Send;
import lombok.Getter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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

    private PluginManager   pluginManager;
    private PlayersManager  playersManager;

    private Config configuration;

    private DatabasesManager    databasesManager;
    private Database            database;
    private boolean             databaseEnabled;

    private Send send;

    private ReloadCommand   reloadCommand;
    private VersionCommand  versionCommand;

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        aevyCore = this;

        /* Loads the configuration. */
        configuration   = new Config(this, ConfigType.DEFAULT);
        send            = new Send(configuration);

        String support = "messages";

        configuration.add(GlobalEntries.PREFIX,        support);
        configuration.add(GlobalEntries.NO_PERMS,      support);
        configuration.add(GlobalEntries.NO_PLAYER,     support);
        configuration.add(GlobalEntries.NO_CONSOLE,    support);

        support = "permissions";

        configuration.add(AevyCoreEntries.RELOAD_PERM, support);

        support = "commands";

        configuration.add(AevyCoreEntries.RELOAD_MESSAGE,   support);
        configuration.add(AevyCoreEntries.VERSION_MESSAGE,  support);

        support = "database";

        // DRIVER, URL, IP, PORT, USER, PASSWORD, DATABASE, MAX_POOL_SIZE, DEBUG
        configuration.add(DatabaseEntries.ENABLED,          support);

        databaseEnabled = (Boolean) configuration.get(DatabaseEntries.ENABLED).getValue();

        if (databaseEnabled)
        {
            configuration.add(DatabaseEntries.DRIVER,           support);
            configuration.add(DatabaseEntries.URL,              support);
            configuration.add(DatabaseEntries.IP,               support);
            configuration.add(DatabaseEntries.PORT,             support);
            configuration.add(DatabaseEntries.USER,             support);
            configuration.add(DatabaseEntries.PASSWORD,         support);
            configuration.add(DatabaseEntries.MAX_POOL_SIZE,    support);
            configuration.add(DatabaseEntries.DEBUG,            support);

            databasesManager = new DatabasesManager(this);

            DatabaseConnection databaseConnection = new DatabaseConnection(configuration);
            databasesManager.addConnection("aevycore", databaseConnection);

            database = new Database(this, databasesManager, databaseConnection);
        }

        /* Loads all the useful variables. */
        PluginDescriptionFile descriptionFile = getDescription();
        version = descriptionFile.getVersion();
        site    = descriptionFile.getWebsite();

        pluginManager   = getServer().getPluginManager();
        playersManager  = new PlayersManager(this);

        playersManager.addAllOnlinePlayers();

        /* Creates the /corereload command */
        reloadCommand = new ReloadCommand(
                this,
                send,
                (String) configuration.getValue(AevyCoreEntries.RELOAD_PERM),
                "corereload",
                false,
                false,
                null,
                false
        );
        /* Creates the /coreversion command */
        versionCommand = new VersionCommand(
                this,
                send,
                null,
                "coreversion",
                false,
                false,
                null,
                false
        );

        /* Creates a listener for Player's events. */
        new PlayerEvents(this, send);
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic

        /* Saves the configuration. */
        configuration.save();

        if (databaseEnabled)
        {
            /* Closes every database and database connection. */
            databasesManager.shutdown();
        }
    }

    public static AevyCore getInstance()
    {
        return aevyCore;
    }

}

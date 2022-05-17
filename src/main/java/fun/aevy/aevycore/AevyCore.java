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
import fun.aevy.aevycore.utils.configuration.entries.DatabaseEntries;
import fun.aevy.aevycore.utils.configuration.entries.DefaultEntries;
import fun.aevy.aevycore.utils.formatting.Send;
import fun.aevy.aevycore.utils.strings.StringUtils;
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
    private String          version, site;

    private PluginManager   pluginManager;
    private PlayersManager  playersManager;

    private Config          configuration;
    private StringUtils     stringUtils;
    private Send            send;

    private DatabasesManager    databasesManager;
    private Database            database;

    @Override
    public void onEnable()
    {
        // Plugin startup logic

        /* Loads the configuration. */
        configuration = new Config(this, ConfigType.DEFAULT);

        String support = "messages";

        configuration.add(DefaultEntries.PREFIX,   support);
        configuration.add(DefaultEntries.NORMAL,   support);
        configuration.add(DefaultEntries.ERROR,    support);
        configuration.add(DefaultEntries.SUCCESS,  support);

        support += ".errors";

        configuration.add(DefaultEntries.NO_PLAYER,     support);
        configuration.add(DefaultEntries.NO_PERMS,      support);
        configuration.add(DefaultEntries.NO_CONSOLE,    support);

        support = "permissions";

        configuration.add(DefaultEntries.RELOAD_PERM, support);

        support = "commands";

        configuration.add(DefaultEntries.RELOAD_MESSAGE, support);

        support = "database";

        // DRIVER, URL, IP, PORT, USER, PASSWORD, DATABASE, MAX_POOL_SIZE, DEBUG
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

        /* Loads all the useful variables. */
        PluginDescriptionFile descriptionFile = getDescription();
        version = descriptionFile.getVersion();
        site    = descriptionFile.getWebsite();

        pluginManager = getServer().getPluginManager();

        stringUtils     = new StringUtils(this);
        send            = new Send(this);
        playersManager  = new PlayersManager(this);

        playersManager.addAllOnlinePlayers();

        /* Creates the /corereload command */
        new ReloadCommand(
                this,
                this,
                (String) configuration.getValue(DefaultEntries.RELOAD_PERM),
                "corereload",
                false,
                false,
                null,
                false
        );
        /* Creates the /coreversion command */
        new VersionCommand(
                this,
                this,
                null,
                "coreversion",
                false,
                false,
                null,
                false
        );

        /* Creates a listener for Player's events. */
        new PlayerEvents(this, this);
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic

        /* Saves the configuration. */
        configuration.save();

        /* Closes every database and database connection. */
        databasesManager.shutdown();
    }

}

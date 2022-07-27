package fun.aevy.aevycore;

import fun.aevy.aevycore.commands.ReloadCommand;
import fun.aevy.aevycore.commands.VersionCommand;
import fun.aevy.aevycore.struct.elements.database.Database;
import fun.aevy.aevycore.struct.elements.database.DatabaseConnection;
import fun.aevy.aevycore.struct.manager.DatabasesManager;
import fun.aevy.aevycore.utils.configuration.elements.ConfigType;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
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

    private PluginManager pluginManager;

    private DatabasesManager    databasesManager;
    private Database            database;
    private boolean             databaseEnabled;

    private CoolConfig coolConfig;
    private Send send;

    private ReloadCommand   reloadCommand;
    private VersionCommand  versionCommand;

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        aevyCore = this;

        /* Loads the configuration. */
        coolConfig = new CoolConfig(this, ConfigType.DEFAULT);
        send       = new Send(coolConfig);

        String support = "messages";

        coolConfig.add(GlobalEntries.PREFIX,        support);
        coolConfig.add(GlobalEntries.NO_PERMS,      support);
        coolConfig.add(GlobalEntries.NO_PLAYER,     support);
        coolConfig.add(GlobalEntries.NO_CONSOLE,    support);

        support = "permissions";

        coolConfig.add(AevyCoreEntries.RELOAD_PERM, support);

        support = "commands";

        coolConfig.add(AevyCoreEntries.RELOAD_MESSAGE,   support);
        coolConfig.add(AevyCoreEntries.VERSION_MESSAGE,  support);

        support = "database";

        // DRIVER, URL, IP, PORT, USER, PASSWORD, DATABASE, MAX_POOL_SIZE, DEBUG
        coolConfig.add(DatabaseEntries.ENABLED,          support);

        databaseEnabled = (Boolean) coolConfig.get(DatabaseEntries.ENABLED).getValue();

        if (databaseEnabled)
        {
            coolConfig.add(DatabaseEntries.DRIVER,           support);
            coolConfig.add(DatabaseEntries.URL,              support);
            coolConfig.add(DatabaseEntries.IP,               support);
            coolConfig.add(DatabaseEntries.PORT,             support);
            coolConfig.add(DatabaseEntries.USER,             support);
            coolConfig.add(DatabaseEntries.PASSWORD,         support);
            coolConfig.add(DatabaseEntries.MAX_POOL_SIZE,    support);
            coolConfig.add(DatabaseEntries.DEBUG,            support);

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

        String reloadPerm = (String) coolConfig.getValue(AevyCoreEntries.RELOAD_PERM);

        reloadCommand = new ReloadCommand(this, reloadPerm, "aevyreload");
        versionCommand = new VersionCommand(this, null, "aevyversion");
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic

        /* Saves the configuration. */
        coolConfig.save();

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

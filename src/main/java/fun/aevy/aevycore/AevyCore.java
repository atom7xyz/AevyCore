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

        coolConfig.setTempPath("permissions");
        coolConfig.add(Aevy.Perms.RELOAD);

        coolConfig.setTempPath("messages");
        coolConfig.add(Aevy.Messages.PREFIX);
        coolConfig.add(Aevy.Global.NO_PERMS);
        coolConfig.add(Aevy.Global.NO_PLAYER);
        coolConfig.add(Aevy.Global.NO_CONSOLE);
        coolConfig.add(Aevy.Global.UNKNOWN_PLAYER);

        coolConfig.setTempPath("messages.commands.aevy");
        coolConfig.add(Aevy.Messages.RELOAD);
        coolConfig.add(Aevy.Messages.VERSION);

        coolConfig.setTempPath("messages.usages");
        coolConfig.add(Aevy.Usages.AEVY);

        coolConfig.setTempPath("database");
        coolConfig.add(Aevy.Database.ENABLED);

        databaseEnabled = (Boolean) coolConfig.getValue(Aevy.Database.ENABLED);
        if (databaseEnabled)
        {
            coolConfig.add(Aevy.Database.DATABASE);
            coolConfig.add(Aevy.Database.DRIVER);
            coolConfig.add(Aevy.Database.URL);
            coolConfig.add(Aevy.Database.IP);
            coolConfig.add(Aevy.Database.PORT);
            coolConfig.add(Aevy.Database.USER);
            coolConfig.add(Aevy.Database.PASSWORD);
            coolConfig.add(Aevy.Database.MAX_POOL_SIZE);
            coolConfig.add(Aevy.Database.DEBUG);
            coolConfig.add(Aevy.Database.USE_DEFAULTS);
            coolConfig.add(Aevy.Database.PROPERTIES);

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

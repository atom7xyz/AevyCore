package fun.aevy.aevycore;

import fun.aevy.aevycore.commands.AevyCommand;
import fun.aevy.aevycore.struct.elements.AevyDependent;
import fun.aevy.aevycore.struct.elements.database.Database;
import fun.aevy.aevycore.struct.elements.database.DatabaseConnection;
import fun.aevy.aevycore.struct.elements.events.EventListener;
import fun.aevy.aevycore.struct.manager.DatabasesManager;
import fun.aevy.aevycore.utils.configuration.entries.Aevy;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;

/**
 * AevyCore's main class.
 * @since 1.0
 * @author Sorridi
 */
@Getter
public final class AevyCore extends AevyDependent
{
    private static AevyCore aevyCore;

    private PluginManager pluginManager;

    private DatabasesManager    databasesManager;
    private Database            database;
    private boolean             databaseEnabled;

    @Override
    public void onEnable()
    {
        enable(this);

        aevyCore        = this;
        pluginManager   = getServer().getPluginManager();

        databaseEnabled = (Boolean) coolConfig.getValue(Aevy.Database.ENABLED);
        
        if (databaseEnabled)
        {
            databasesManager = new DatabasesManager(this);

            DatabaseConnection databaseConnection = new DatabaseConnection(coolConfig);
            databasesManager.addConnection("AevyCore", databaseConnection);

            database = new Database(this, databasesManager, databaseConnection);
            database.debug((Boolean) coolConfig.getValue(Aevy.Database.DEBUG));
        }

        new AevyCommand(this, "aevy")
                .setUsage(Aevy.Usages.AEVY)
                .setTabComplete(true)
                .build();

        new EventListener(this);
    }

    @Override
    public void onDisable()
    {
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
package fun.aevy.aevycore;

import fun.aevy.aevycore.commands.ReloadCommand;
import fun.aevy.aevycore.commands.VersionCommand;
import fun.aevy.aevycore.events.PlayerEvents;
import fun.aevy.aevycore.struct.manager.PlayersManager;
import fun.aevy.aevycore.utils.configuration.Config;
import fun.aevy.aevycore.utils.configuration.ConfigType;
import fun.aevy.aevycore.utils.configuration.entries.MessageEntries;
import fun.aevy.aevycore.utils.configuration.entries.MiscEntries;
import fun.aevy.aevycore.utils.configuration.entries.PermissionEntries;
import fun.aevy.aevycore.utils.formatting.Send;
import fun.aevy.aevycore.utils.strings.StringUtils;
import lombok.Getter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class AevyCore extends JavaPlugin
{
    private String version, site;
    private PluginManager pluginManager;
    private PlayersManager playersManager;
    private Config configuration;
    private StringUtils stringUtils;
    private Send send;

    @Override
    public void onEnable()
    {
        // Plugin startup logic

        /* Loads the configuration. */
        configuration = new Config(this, ConfigType.DEFAULT);

        String support = "messages";

        configuration.add(MiscEntries.PREFIX,   support);
        configuration.add(MiscEntries.NORMAL,   support);
        configuration.add(MiscEntries.ERROR,    support);
        configuration.add(MiscEntries.SUCCESS,  support);

        support += ".errors";

        configuration.add(MessageEntries.NO_PLAYER,     support);
        configuration.add(MessageEntries.NO_PERMS,      support);
        configuration.add(MessageEntries.NO_CONSOLE,    support);

        support = "permissions";

        configuration.add(PermissionEntries.RELOAD_PERM, support);

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
                (String) configuration.getValue(PermissionEntries.RELOAD_PERM),
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
    }

}

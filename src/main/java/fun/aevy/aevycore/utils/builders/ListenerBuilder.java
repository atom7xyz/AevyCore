package fun.aevy.aevycore.utils.builders;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.struct.manager.PlayersManager;
import fun.aevy.aevycore.utils.formatting.Send;
import fun.aevy.aevycore.utils.strings.StringUtils;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ListenerBuilder implements Listener
{
    protected final AevyCore        aevyCore;
    protected final PlayersManager  playersManager;
    protected final Send            send;
    protected final StringUtils     stringUtils;

    /**
     * Constructor for new Listeners, which they get automatically registered.
     * @param javaPlugin Instance of the plugin
     */
    public ListenerBuilder(JavaPlugin javaPlugin, AevyCore aevyCore)
    {
        this.aevyCore       = aevyCore;
        this.playersManager = aevyCore.getPlayersManager();
        this.send           = aevyCore.getSend();
        this.stringUtils    = aevyCore.getStringUtils();

        javaPlugin.getServer().getPluginManager().registerEvents(this, javaPlugin);
    }

}

package fun.aevy.aevycore.utils.builders;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.struct.manager.PlayersManager;
import fun.aevy.aevycore.utils.formatting.Send;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Creates and register new {@link Listener}s. Utility classes are automatically provided.
 * @since 1.0
 * @author Sorridi
 */
public abstract class ListenerBuilder implements Listener
{
    protected final AevyCore        aevyCore;
    protected final PlayersManager  playersManager;
    protected final Send            send;

    /**
     * Constructor for new Listeners, which they get automatically registered.
     * @param javaPlugin Instance of the plugin
     * @since 1.0
     */
    public ListenerBuilder(JavaPlugin javaPlugin, Send send)
    {
        this.aevyCore       = AevyCore.getInstance();
        this.playersManager = aevyCore.getPlayersManager();
        this.send           = send;

        javaPlugin.getServer().getPluginManager().registerEvents(this, javaPlugin);
    }

}

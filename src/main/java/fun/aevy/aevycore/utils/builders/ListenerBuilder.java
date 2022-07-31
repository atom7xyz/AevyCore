package fun.aevy.aevycore.utils.builders;

import fun.aevy.aevycore.struct.elements.AevyDependent;
import fun.aevy.aevycore.struct.elements.Reloadable;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
import fun.aevy.aevycore.utils.formatting.Send;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

/**
 * Creates and register new {@link Listener}s. Utility classes are automatically provided.
 * @since 1.0
 * @author Sorridi
 */
public abstract class ListenerBuilder implements Listener, Reloadable
{
    protected final AevyDependent   aevyDependent;
    protected final PluginManager   pluginManager;
    protected final CoolConfig      coolConfig;
    protected final Send            send;

    /**
     * Constructor for new Listeners, which they get automatically registered.
     * @param aevyDependent Instance of the AevyDependent plugin.
     */
    public ListenerBuilder(AevyDependent aevyDependent)
    {
        this.aevyDependent  = aevyDependent;
        this.pluginManager  = aevyDependent.getServer().getPluginManager();
        this.coolConfig     = aevyDependent.getCoolConfig();
        this.send           = aevyDependent.getSend();

        aevyDependent.addReloadable(this);

        registerEvents();
        reloadVars();
    }

    /**
     * Registers new events, bound to this class.
     */
    public void registerEvents()
    {
        pluginManager.registerEvents(this, aevyDependent);
    }

    /**
     * Unregister events bounded to this class.
     */
    public void unregisterEvents()
    {
        HandlerList.unregisterAll(this);
    }

}

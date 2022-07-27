package fun.aevy.aevycore.utils.builders;

import fun.aevy.aevycore.struct.elements.AevyDependent;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

/**
 * Creates and register new {@link Listener}s. Utility classes are automatically provided.
 * @since 1.0
 * @author Sorridi
 */
public abstract class ListenerBuilder implements Listener
{
    protected final AevyDependent   aevyDependent;
    protected final PluginManager   pluginManager;

    /**
     * Constructor for new Listeners, which they get automatically registered.
     * @param aevyDependent Instance of the AevyDependent plugin.
     */
    public ListenerBuilder(AevyDependent aevyDependent)
    {
        this.aevyDependent  = aevyDependent;
        this.pluginManager  = aevyDependent.getServer().getPluginManager();

        registerEvents();
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

package fun.aevy.aevycore.struct.elements.events;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.struct.elements.AevyDependent;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event called when a reload request is sent.
 * @since 1.7
 * @author Sorridi
 */
@Getter
public class ConfigReloadEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();

    private final AevyDependent aevyDependent;
    private final AevyCore      aevyCore;

    /**
     * Constructor.
     * @param aevyDependent The AevyDependent instance that sent the reload request.
     */
    public ConfigReloadEvent(AevyDependent aevyDependent)
    {
        this.aevyDependent  = aevyDependent;
        this.aevyCore       = null;
    }

    /**
     * Constructor.
     * @param aevyCore The AevyCore instance that sent the reload request.
     */
    public ConfigReloadEvent(AevyCore aevyCore)
    {
        this.aevyDependent  = null;
        this.aevyCore       = aevyCore;
    }

    public HandlerList getHandlers()
    {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}

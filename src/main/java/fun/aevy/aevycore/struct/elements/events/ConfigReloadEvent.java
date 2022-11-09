package fun.aevy.aevycore.struct.elements.events;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.struct.elements.AevyDependent;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class ConfigReloadEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();

    private final AevyDependent aevyDependent;
    private final AevyCore      aevyCore;

    public ConfigReloadEvent(AevyDependent aevyDependent)
    {
        this.aevyDependent  = aevyDependent;
        this.aevyCore       = null;
    }

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

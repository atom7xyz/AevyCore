package fun.aevy.aevycore.struct.elements.events;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.utils.builders.ListenerBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class EventListener extends ListenerBuilder
{
    public EventListener(AevyCore aevyCore)
    {
        super(aevyCore);
    }

    @EventHandler
    public void on(ConfigReloadEvent event)
    {
        JavaPlugin plugin = event.getAevyCore();

        if (plugin != null)
        {
            event.getAevyCore().reloadReloadables();
        }
    }

    @Override
    public void reloadVars()
    {

    }
}

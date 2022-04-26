package fun.aevy.aevycore.events;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.struct.elements.AevyPlayer;
import fun.aevy.aevycore.utils.builders.ListenerBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerEvents extends ListenerBuilder
{
    public PlayerEvents(JavaPlugin javaPlugin, AevyCore aevyCore)
    {
        super(javaPlugin, aevyCore);
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void on(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        playersManager.addList(new AevyPlayer(player));
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void on(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        playersManager.removeList(playersManager.getFromList(player));
    }

}

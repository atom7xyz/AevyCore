package fun.aevy.aevycore.events;

import fun.aevy.aevycore.struct.elements.AevyPlayer;
import fun.aevy.aevycore.struct.manager.PlayersManager;
import fun.aevy.aevycore.utils.builders.Lambda;
import fun.aevy.aevycore.utils.builders.ListenerBuilder;
import fun.aevy.aevycore.utils.formatting.Send;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * The player events for managing the list of online {@link AevyPlayer}s using
 * {@link PlayersManager}
 * @since 1.0
 * @author Sorridi
 */
public class PlayerEvents extends ListenerBuilder
{

    /**
     * Constructor for new Listeners, which they get automatically registered.
     *
     * @param javaPlugin    Instance of the plugin
     * @param send          Instance of Send
     * @since 1.0
     */
    public PlayerEvents(JavaPlugin javaPlugin, Send send)
    {
        super(javaPlugin, send);
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void on(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        playersManager.addSet(new AevyPlayer(player));
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void on(PlayerQuitEvent event)
    {
        Player player           = event.getPlayer();
        AevyPlayer aevyPlayer   = playersManager.getItem(new Lambda()
        {
            @Override
            public <G> boolean expression(@NotNull G g)
            {
                return ((AevyPlayer) g).getPlayer().equals(player);
            }
        });
        playersManager.removeSet(aevyPlayer);
    }

}

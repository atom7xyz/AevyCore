package fun.aevy.aevycore.struct.manager;

import fun.aevy.aevycore.struct.elements.AevyPlayer;
import fun.aevy.aevycore.utils.builders.ManagerBuilder;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Manages the {@link AevyPlayer}s.
 * @since 1.0
 * @author Sorridi
 */
public class PlayersManager extends ManagerBuilder<AevyPlayer, ObjectUtils.Null>
{

    /**
     * Constructor for new Managers.
     *
     * @param javaPlugin Instance of the plugin.
     * @since 1.0
     */
    public PlayersManager(JavaPlugin javaPlugin)
    {
        super(javaPlugin);
    }

    /**
     * Adds all online players to the managers' HashSet.
     */
    public void addAllOnlinePlayers()
    {
        javaPlugin
                .getServer()
                .getOnlinePlayers()
                .forEach(player -> addSet(new AevyPlayer(player)));
    }
}

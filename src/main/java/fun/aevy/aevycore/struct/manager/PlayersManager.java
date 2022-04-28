package fun.aevy.aevycore.struct.manager;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.struct.elements.AevyPlayer;
import fun.aevy.aevycore.utils.builders.Lambda;
import fun.aevy.aevycore.utils.builders.ManagerBuilder;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.entity.Player;

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
     * @param aevyCore Instance of AevyCore.
     * @since 1.0
     */
    public PlayersManager(AevyCore aevyCore)
    {
        super(aevyCore);
    }

    /**
     * Adds all online players to the managers' list.
     */
    public void addAllOnlinePlayers()
    {
        aevyCore
                .getServer()
                .getOnlinePlayers()
                .forEach(player -> addSet(new AevyPlayer(player)));
    }
}

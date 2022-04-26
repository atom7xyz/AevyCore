package fun.aevy.aevycore.struct.manager;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.struct.elements.AevyPlayer;
import fun.aevy.aevycore.utils.builders.ManagerBuilder;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.entity.Player;

public class PlayersManager extends ManagerBuilder<AevyPlayer, ObjectUtils.Null>
{
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
                .forEach(player -> addList(new AevyPlayer(player)));
    }

    @Override
    public <K> AevyPlayer getFromList(K generic)
    {
        return  list
                .stream()
                .filter(g -> equals(g, generic))
                .findAny()
                .orElse(null);
    }

    @Override
    public ObjectUtils.Null getFromMap(AevyPlayer generic)
    {
        return null;
    }

    @Override
    public <V, K> boolean equals(V type, K input)
    {
        if (input instanceof AevyPlayer)
            return type.equals(input);

        if (input instanceof Player)
            return type.equals(input);

        if (input instanceof String)
            return ((Player) type).getName().equals(input);

        return false;
    }

}

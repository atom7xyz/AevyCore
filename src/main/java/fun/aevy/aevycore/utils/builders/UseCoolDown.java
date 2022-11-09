package fun.aevy.aevycore.utils.builders;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
public class UseCoolDown
{
    private final HashMap<Player, Long> coolDowns;
    private final long time;

    public UseCoolDown(long time)
    {
        coolDowns = new HashMap<>();
        this.time = time * 1000;
    }

    public void put(Player player)
    {
        coolDowns.putIfAbsent(player, 0L);
    }

    public void renew(Player player)
    {
        coolDowns.replace(player, System.currentTimeMillis());
    }

    public void remove(Player player)
    {
        coolDowns.remove(player);
    }

    public void clear()
    {
        coolDowns.clear();
    }

    public long timeDiff(Player player)
    {
        return System.currentTimeMillis() - coolDowns.get(player);
    }

    public long usableIn(Player player)
    {
        return time - timeDiff(player);
    }

    public boolean isUsable(Player player)
    {
        return timeDiff(player) >= time;
    }
}
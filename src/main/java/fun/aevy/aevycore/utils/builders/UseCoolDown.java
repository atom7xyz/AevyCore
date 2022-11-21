package fun.aevy.aevycore.utils.builders;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Creates a bound between a player and time. Useful for cooldowns.
 */
@Getter
public class UseCoolDown
{
    private final HashMap<Player, Long> coolDowns;
    private final long time;

    /**
     * Constructor for a new UseCoolDown.
     * @param time Time in seconds.
     */
    public UseCoolDown(long time)
    {
        coolDowns = new HashMap<>();
        this.time = time * 1000;
    }

    /**
     * Puts a player in the cooldown.
     * @param player Player to be put in the cooldown.
     */
    public void put(Player player)
    {
        coolDowns.putIfAbsent(player, 0L);
    }

    /**
     * Renews the cooldown for a player.
     * @param player The player.
     */
    public void renew(Player player)
    {
        coolDowns.replace(player, System.currentTimeMillis());
    }

    /**
     * Removes a player from the cooldown.
     * @param player Player to be removed.
     */
    public void remove(Player player)
    {
        coolDowns.remove(player);
    }

    /**
     * Clears cooldowns.
     */
    public void clear()
    {
        coolDowns.clear();
    }

    /**
     * Checks the time difference between the current time and the players' last cooldown entry.
     * @param player Player to check.
     * @return The time difference.
     */
    public long timeDiff(Player player)
    {
        return System.currentTimeMillis() - coolDowns.get(player);
    }

    /**
     * Retrieves the cooldown time left for a player.
     * @param player Player to check.
     * @return Time left in milliseconds.
     */
    public long usableIn(Player player)
    {
        return time - timeDiff(player);
    }

    /**
     * Checks if the cooldown has expired.
     * @param player Player to check.
     * @return {@code true} if the cooldown has expired, false otherwise.
     */
    public boolean isUsable(Player player)
    {
        return timeDiff(player) >= time;
    }
}
package fun.aevy.aevycore.utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.stream.Stream;

/**
 * Utility class used to play sounds.
 */
public class SoundUtils
{
    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     */
    public static void play(Player player, Sound sound)
    {
        player.playSound(player.getLocation(), sound, 1, 0);
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     * @param location  Location to play the sound at.
     */
    public static void play(Player player, Sound sound, Location location)
    {
        player.playSound(location, sound, 1, 0);
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     */
    public static void play(Player player, Sound sound, int volume)
    {
        player.playSound(player.getLocation(), sound, volume, 0);
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     * @param pitch     Pitch of the sound.
     */
    public static void play(Player player, Sound sound, int volume, int pitch)
    {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     * @param pitch     Pitch of the sound.
     * @param location  Location to play the sound at.
     */
    public static void play(Player player, Sound sound, int volume, int pitch, Location location)
    {
        player.playSound(location, sound, volume, pitch);
    }

    /**
     * Gets the players within a certain radius of an entity.
     * @param entity    Entity to get the players around.
     * @param range     Range to get the players in.
     * @return          Stream of players within the range.
     */
    private static Stream<Entity> toPlayers(Entity entity, int range)
    {
        return entity
                .getNearbyEntities(range, range, range)
                .stream()
                .filter(e -> e instanceof Player);
    }

    /**
     * Plays a sound to players nearby an entity.
     * @param sound     Sound to play.
     * @param entity    Entity to play the sound to.
     * @param range     Range of the sound.
     */
    public static void playNearby(Sound sound, Entity entity, int range)
    {
        toPlayers(entity, range).forEach(e -> play((Player) e, sound));
    }

    /**
     * Plays a sound to players nearby an entity.
     * @param sound     Sound to play.
     * @param entity    Entity to play the sound to.
     * @param volume    Volume of the sound.
     * @param pitch     Pitch of the sound.
     * @param range     Range of the sound.
     */
    public static void playNearby(Sound sound, Entity entity, int volume, int pitch, int range)
    {
        toPlayers(entity, range).forEach(e -> play((Player) e, sound, volume, pitch));
    }

    /**
     * Plays a sound to players nearby an entity.
     * @param location  Location to play the sound at.
     * @param sound     Sound to play.
     * @param entity    Entity to play the sound to.
     * @param range     Range of the sound.
     */
    public static void playNearby(Location location, Sound sound, Entity entity, int range)
    {
        toPlayers(entity, range).forEach(e -> play((Player) e, sound, location));
    }

    /**
     * Plays a sound to players nearby an entity.
     * @param location  Location to play the sound at.
     * @param sound     Sound to play.
     * @param entity    Entity to play the sound to.
     * @param volume    Volume of the sound.
     * @param pitch     Pitch of the sound.
     * @param range     Range of the sound.
     */
    public static void playNearby(Location location, Sound sound, Entity entity, int volume, int pitch, int range)
    {
        toPlayers(entity, range).forEach(e -> play((Player) e, sound, volume, pitch, location));
    }

}
package fun.aevy.aevycore.utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class SoundUtils
{
    public static void play(Player player, Sound sound)
    {
        player.playSound(player.getLocation(), sound, 1, 0);
    }

    public static void play(Player player, Sound sound, Location location)
    {
        player.playSound(location, sound, 1, 0);
    }

    public static void play(Player player, Sound sound, int volume)
    {
        player.playSound(player.getLocation(), sound, 1, 0);
    }

    public static void play(Player player, Sound sound, int volume, int pitch)
    {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static void play(Player player, Sound sound, int volume, int pitch, Location location)
    {
        player.playSound(location, sound, volume, pitch);
    }

    public static void playNearby(Sound sound, Entity entity, int range)
    {
        entity
                .getNearbyEntities(range, range, range)
                .stream()
                .filter(e -> e instanceof Player)
                .forEach(e -> play((Player) e, sound));
    }

    public static void playNearby(Location location, Sound sound, Entity entity, int range)
    {
        entity
                .getNearbyEntities(range, range, range)
                .stream()
                .filter(e -> e instanceof Player)
                .forEach(e -> play((Player) e, sound, location));
    }
}

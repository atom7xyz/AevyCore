package fun.aevy.aevycore.utils.tasks;

import fun.aevy.aevycore.struct.elements.AevyDependent;
import fun.aevy.aevycore.struct.elements.scheduler.Scheduler;
import fun.aevy.aevycore.utils.SoundUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

/**
 * Task for playing a sound to a player.
 * @author Sorridi
 * @since 1.0
 */
public class PlaySoundTask extends Scheduler
{
    private int toRepeatFor;

    /**
     * Creates a new {@link PlaySoundTask}.
     * @param aevyDependent The {@link AevyDependent} instance.
     * @param delay         The delay.
     * @param repeat        The repeat time.
     * @param toRepeatFor   The amount of times to repeat.
     */
    public PlaySoundTask(AevyDependent aevyDependent, int delay, int repeat, int toRepeatFor)
    {
        super(aevyDependent);

        delay(delay, TimeUnit.SECONDS);

        if (repeat > 0)
        {
            repeat(repeat, TimeUnit.SECONDS);
        }

        this.toRepeatFor = toRepeatFor;
    }

    /**
     * Creates a new {@link PlaySoundTask}.
     * @param aevyDependent The {@link AevyDependent} instance.
     * @param repeat        The repeat time.
     * @param toRepeatFor   The amount of times to repeat.
     */
    public PlaySoundTask(AevyDependent aevyDependent, int repeat, int toRepeatFor)
    {
        super(aevyDependent);

        delay(0, TimeUnit.SECONDS);

        if (repeat > 0)
        {
            repeat(repeat, TimeUnit.SECONDS);
        }

        this.toRepeatFor = toRepeatFor;
    }

    /**
     * Decrements the time to repeat the sound for.
     */
    private void dec()
    {
        if (--toRepeatFor <= 0)
        {
            cancel();
        }
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     */
    public void runTask(Player player, Sound sound)
    {
        runTask(() ->
        {
            SoundUtils.play(player, sound);
            dec();
        });
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     */
    public void runTask(Player player, Sound sound, int volume)
    {
        runTask(() ->
        {
            SoundUtils.play(player, sound, volume);
            dec();
        });
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     * @param pitch     Pitch of the sound.
     */
    public void runTask(Player player, Sound sound, int volume, int pitch)
    {
        runTask(() ->
        {
            SoundUtils.play(player, sound, volume, pitch);
            dec();
        });
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     * @param location  Location to play the sound at.
     */
    public void runTask(Player player, Sound sound, Location location)
    {
        runTask(() ->
        {
            SoundUtils.play(player, sound, location);
            dec();
        });
    }

    /**
     * Plays a sound to a player.
     * @param player    Player to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     * @param pitch     Pitch of the sound.
     * @param location  Location to play the sound at.
     */
    public void runTask(Player player, Sound sound, int volume, int pitch, Location location)
    {
        runTask(() ->
        {
            SoundUtils.play(player, sound, volume, pitch, location);
            dec();
        });
    }

    /**
     * Plays a sound to an entity.
     * @param entity    Entity to play the sound to.
     * @param sound     Sound to play.
     */
    public void runTask(Sound sound, Entity entity, int range)
    {
        runTask(() ->
        {
            SoundUtils.playNearby(sound, entity, range);
            dec();
        });
    }

    /**
     * Plays a sound to an entity.
     * @param entity    Entity to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     */
    public void runTask(Sound sound, Entity entity, int volume, int pitch, int range)
    {
        runTask(() ->
        {
            SoundUtils.playNearby(sound, entity, volume, pitch, range);
            dec();
        });
    }

    /**
     * Plays a sound to an entity.
     * @param location  Location to play the sound at.
     * @param sound     Sound to play.
     * @param entity    Entity to play the sound to.
     * @param range     Range to play the sound at.
     */
    public void runTask(Location location, Sound sound, Entity entity, int range)
    {
        runTask(() ->
        {
            SoundUtils.playNearby(location, sound, entity, range);
            dec();
        });
    }

    /**
     * Plays a sound to an entity.
     * @param entity    Entity to play the sound to.
     * @param sound     Sound to play.
     * @param volume    Volume of the sound.
     * @param pitch     Pitch of the sound.
     */
    public void runTask(Location location, Sound sound, Entity entity, int volume, int pitch, int range)
    {
        runTask(() ->
        {
            SoundUtils.playNearby(location, sound, entity, volume, pitch, range);
            dec();
        });
    }

    @Override
    public void runTask(Object... objects)
    {

    }
}
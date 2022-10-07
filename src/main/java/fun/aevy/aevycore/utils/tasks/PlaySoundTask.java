package fun.aevy.aevycore.utils.tasks;

import fun.aevy.aevycore.struct.elements.AevyDependent;
import fun.aevy.aevycore.struct.elements.scheduler.Scheduler;
import fun.aevy.aevycore.utils.SoundUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class PlaySoundTask extends Scheduler
{

    private int toRepeatFor;

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

    private void dec()
    {
        if (--toRepeatFor <= 0)
        {
            cancel();
        }
    }

    public void runTask(Player player, Sound sound)
    {
        runTask(() ->
        {
            SoundUtils.play(player, sound);
            dec();
        });
    }

    public void runTask(Player player, Sound sound, int volume)
    {
        runTask(() ->
        {
            SoundUtils.play(player, sound, volume);
            dec();
        });
    }

    public void runTask(Player player, Sound sound, int volume, int pitch)
    {
        runTask(() ->
        {
            SoundUtils.play(player, sound, volume, pitch);
            dec();
        });
    }

    public void runTask(Player player, Sound sound, Location location)
    {
        runTask(() ->
        {
            SoundUtils.play(player, sound, location);
            dec();
        });
    }

    public void runTask(Player player, Sound sound, int volume, int pitch, Location location)
    {
        runTask(() ->
        {
            SoundUtils.play(player, sound, volume, pitch, location);
            dec();
        });
    }

    public void runTask(Sound sound, Entity entity, int range)
    {
        runTask(() ->
        {
            SoundUtils.playNearby(sound, entity, range);
            dec();
        });
    }

    public void runTask(Sound sound, Entity entity, int volume, int pitch, int range)
    {
        runTask(() ->
        {
            SoundUtils.playNearby(sound, entity, volume, pitch, range);
            dec();
        });
    }

    public void runTask(Location location, Sound sound, Entity entity, int range)
    {
        runTask(() ->
        {
            SoundUtils.playNearby(location, sound, entity, range);
            dec();
        });
    }

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

package fun.aevy.aevycore.struct.elements.scheduler;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Creates and executes new {@link BukkitTask}s using {@link BukkitScheduler}. <br>
 * Based on <a href="https://github.com/hakan-krgn/hCore">https://github.com/hakan-krgn/hCore</a>
 * @since 1.4
 * @author Sorridi
 */
public class Scheduler
{
    private final JavaPlugin        javaPlugin;
    private final Logger            logger;
    private final BukkitScheduler   bukkitScheduler;
    private final String            prefix;

    private BukkitTask bukkitTask;

    private long    initialDelay, repeatEvery;
    private boolean async, repeatable;

    /**
     * Constructor for new Schedulers.
     * @param javaPlugin The instance of the plugin.
     */
    public Scheduler(JavaPlugin javaPlugin)
    {
        this.javaPlugin         = javaPlugin;
        this.logger             = javaPlugin.getLogger();
        this.bukkitScheduler    = javaPlugin.getServer().getScheduler();
        this.prefix             = "Scheduler: ";
    }

    /**
     * Sets task initial delay in ticks.
     * @param initialDelay  The delay.
     * @return              Instance of the Scheduler.
     */
    public Scheduler delay(long initialDelay)
    {
        this.initialDelay = initialDelay;
        return this;
    }

    /**
     * Sets task initial delay with the preferred {@link TimeUnit}.
     * @param initialDelay  The delay.
     * @param timeUnit      The unit of the delay.
     * @return              Instance of the Scheduler.
     */
    public Scheduler delay(long initialDelay, TimeUnit timeUnit)
    {
        initialDelay = timeUnit.toSeconds(initialDelay) / 20;
        return this;
    }

    /**
     * Sets task initial delay in ticks.
     * @param repeatEvery   The delay (must be > 0).
     * @return              Instance of the Scheduler.
     */
    public Scheduler repeat(long repeatEvery)
    {
        if (repeatEvery <= 0)
        {
            logger.warning(prefix + "value of `repeatEvery` is " + repeatEvery + "!");
            return this;
        }
        repeatable = true;
        this.repeatEvery = repeatEvery;
        return this;
    }

    /**
     * Sets task initial delay with the preferred {@link TimeUnit}.
     * @param repeatEvery   The delay (must be > 0).
     * @param timeUnit      The unit of the delay.
     * @return              Instance of the Scheduler.
     */
    public Scheduler repeat(long repeatEvery, TimeUnit timeUnit)
    {
        if (repeatEvery <= 0)
        {
            logger.warning(prefix + "value of `repeatEvery` is " + repeatEvery + "!");
            return this;
        }
        repeatable = true;
        this.repeatEvery = timeUnit.toSeconds(repeatEvery) / 20;
        return this;
    }

    /**
     * Sets the task to execute as async.
     * @param async     The value.
     * @return          Instance of the Scheduler.
     */
    public Scheduler async(boolean async)
    {
        this.async = async;
        return this;
    }

    /**
     * Runs the task assigned to the Scheduler.
     * @param runnable The task to execute.
     */
    public void runTask(@NotNull Runnable runnable)
    {
        if (async)
            if (repeatEvery == 0)
                bukkitTask = bukkitScheduler.runTaskTimerAsynchronously(javaPlugin, runnable, initialDelay, repeatEvery);
            else
                bukkitTask = bukkitScheduler.runTaskLaterAsynchronously(javaPlugin, runnable, initialDelay);
        else
            if (repeatEvery == 0)
                bukkitTask = bukkitScheduler.runTaskTimer(javaPlugin, runnable, initialDelay, repeatEvery);
            else
                bukkitTask = bukkitScheduler.runTaskLater(javaPlugin, runnable, initialDelay);
    }

    /**
     * Checks if the task is cancelled.
     * @return Task is cancelled or not.
     */
    public boolean isCancelled()
    {
        return bukkitTask.isCancelled();
    }

    /**
     * Tries to cancel the task.
     */
    public void cancel()
    {
        bukkitTask.cancel();
    }

}

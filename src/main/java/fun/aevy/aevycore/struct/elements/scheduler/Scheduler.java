package fun.aevy.aevycore.struct.elements.scheduler;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.struct.elements.AevyDependent;
import fun.aevy.aevycore.struct.elements.Reloadable;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
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
public class Scheduler implements Reloadable
{
    protected final AevyDependent aevyDependent;
    protected final CoolConfig    coolConfig;

    private final JavaPlugin        javaPlugin;
    private final Logger            logger;

    protected final BukkitScheduler bukkitScheduler;
    protected final String          prefix;

    protected BukkitTask  bukkitTask;
    protected long        initialDelay, repeatEvery;
    protected boolean     async, repeatable;

    /**
     * Constructor for new Schedulers.
     * @param aevyCore The instance of the plugin.
     */
    public Scheduler(AevyCore aevyCore)
    {
        this.aevyDependent      = null;
        this.coolConfig         = aevyCore.getCoolConfig();
        this.javaPlugin         = aevyCore;
        this.logger             = javaPlugin.getLogger();
        this.bukkitScheduler    = javaPlugin.getServer().getScheduler();
        this.prefix             = "Scheduler: ";

        reloadVars();
    }

    /**
     * Constructor for new Schedulers.
     * @param aevyDependent The instance of the plugin.
     */
    public Scheduler(AevyDependent aevyDependent)
    {
        this.aevyDependent      = aevyDependent;
        this.coolConfig         = aevyDependent.getCoolConfig();
        this.javaPlugin         = aevyDependent.getCurrentPlugin();
        this.logger             = javaPlugin.getLogger();
        this.bukkitScheduler    = javaPlugin.getServer().getScheduler();
        this.prefix             = "Scheduler: ";

        reloadVars();
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
            if (repeatable)
                bukkitTask = bukkitScheduler.runTaskLaterAsynchronously(javaPlugin, runnable, initialDelay);
            else
                bukkitTask = bukkitScheduler.runTaskTimerAsynchronously(javaPlugin, runnable, initialDelay, repeatEvery);
        else
            if (repeatable)
                bukkitTask = bukkitScheduler.runTaskLater(javaPlugin, runnable, initialDelay);
            else
                bukkitTask = bukkitScheduler.runTaskTimer(javaPlugin, runnable, initialDelay, repeatEvery);
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

    @Override
    public void reloadVars()
    {

    }
}

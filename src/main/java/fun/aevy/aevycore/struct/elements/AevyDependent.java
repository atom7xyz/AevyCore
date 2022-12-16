package fun.aevy.aevycore.struct.elements;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.utils.configuration.elements.ConfigType;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
import fun.aevy.aevycore.utils.formatting.Send;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Creates AevyDependent plugins.
 * @since 1.6
 * @author Sorridi
 */
@Getter
public abstract class AevyDependent extends JavaPlugin
{
    protected JavaPlugin    currentPlugin;
    protected AevyCore      aevyCore;
    protected Logger        aevyLogger;
    protected CoolConfig    coolConfig, aevyConfig;
    protected Send          send, aevySend;

    private List<Reloadable> reloadables;

    /**
     * Creates a new AevyDependent plugin.
     * @param plugin The plugin that depends on AevyCore.
     */
    public void enable(Plugin plugin)
    {
        AevyCore aevyCore = null;

        if (plugin instanceof AevyCore)
        {
            aevyCore = (AevyCore) plugin;
        }
        else
        {
            aevyCore = AevyCore.getInstance();
        }

        commonLoad(aevyCore);
    }

    private void commonLoad(AevyCore aevyCore)
    {
        this.aevyCore   = aevyCore;
        currentPlugin   = this;
        aevyLogger      = aevyCore.getLogger();
        aevyConfig      = aevyCore.getCoolConfig();
        aevySend        = aevyCore.getSend();

        coolConfig  = new CoolConfig(currentPlugin, ConfigType.DEFAULT);
        coolConfig.addResources(aevyCore);

        send        = new Send(coolConfig);
        reloadables = new ArrayList<>();
    }

    /**
     * Reloads the {@link CoolConfig} of the current plugin.
     */
    public void reloadCoolConfig()
    {
        if (coolConfig != null)
        {
            coolConfig.reload();
        }
    }

    /**
     * Sends an error message to the console.
     * @param message Error message to send.
     */
    public void errorMessage(String message, Object ...args)
    {
        getLogger().severe(String.format(message, args));
    }

    /**
     * Sends a warning message to the console.
     * @param message Warning message to send.
     */
    public void warningMessage(String message, Object ...args)
    {
        getLogger().warning(String.format(message, args));
    }

    /**
     * Sends a message to the console.
     * @param message Message to send.
     */
    public void infoMessage(String message, Object ...args)
    {
        getLogger().info(String.format(message, args));
    }

    /**
     * Registers a reloadable.
     * @param reloadable Reloadable to register.
     */
    public void addReloadable(Reloadable reloadable)
    {
        reloadables.add(reloadable);
        reloadReloadable(reloadable);
    }

    /**
     * Reloads a reloadable.
     * @param reloadable Reloadable to reload.
     */
    public void reloadReloadable(Reloadable reloadable)
    {
        reloadable.reloadVars();
    }

    /**
     * Reloads all reloadables.
     */
    public void reloadReloadables()
    {
        reloadables.forEach(this::reloadReloadable);
    }

    /**
     * Removes a reloadable.
     * @param reloadable Reloadable to remove.
     */
    public void removeReloadable(Reloadable reloadable)
    {
        reloadables.remove(reloadable);
    }

    /**
     * Calls a new {@link Event}.
     * @param event Event to call.
     */
    public static void callEvent(Event event)
    {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

}
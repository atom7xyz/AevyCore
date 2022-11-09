package fun.aevy.aevycore.struct.elements;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.utils.configuration.elements.ConfigType;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
import fun.aevy.aevycore.utils.formatting.Send;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
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
     * Constructor for AevyDependent.
     */
    public void enableWagie()
    {
        currentPlugin = this;

        aevyCore    = AevyCore.getInstance();
        aevyLogger  = aevyCore.getLogger();
        aevyConfig  = aevyCore.getCoolConfig();
        aevySend    = aevyCore.getSend();

        coolConfig  = new CoolConfig(currentPlugin, ConfigType.DEFAULT);
        coolConfig.addResources(aevyCore);

        send        = new Send(coolConfig);
        reloadables = new ArrayList<>();
    }

    /**
     * Reloads the coolConfig of the current plugin.
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

    public void infoMessage(String message, Object ...args)
    {
        getLogger().info(String.format(message, args));
    }

    public void addReloadable(Reloadable reloadable)
    {
        reloadables.add(reloadable);
        reloadReloadable(reloadable);
    }

    public void reloadReloadable(Reloadable reloadable)
    {
        reloadable.reloadVars();
    }

    public void reloadReloadables()
    {
        reloadables.forEach(this::reloadReloadable);
    }

    public void removeReloadable(Reloadable reloadable)
    {
        reloadables.remove(reloadable);
    }

    public static void callEvent(Event event)
    {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

}
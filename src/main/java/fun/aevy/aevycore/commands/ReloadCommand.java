package fun.aevy.aevycore.commands;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.utils.builders.CommandsBuilder;
import fun.aevy.aevycore.utils.configuration.entries.DefaultEntries;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The command /corereload. Reloads the AevyCore's configuration.
 * @since 1.0
 * @author Sorridi
 */
public class ReloadCommand extends CommandsBuilder
{

    /**
     * Constructor for new Commands, which they get automatically registered.
     *
     * @param aevyCore    Instance of AevyCore.
     * @param plugin      The plugin that is using AevyCore as library.
     * @param permission  Permission required to run the command.
     * @param command     The command itself.
     * @param onlyPlayer  Makes the command runnable only to players.
     * @param onlyConsole Makes the command runnable only to players.
     * @param usage       How the command should be run.
     * @param tabComplete Enables tab complete.
     */
    public ReloadCommand(
            @NotNull    AevyCore aevyCore,
            @NotNull    JavaPlugin plugin,
            @Nullable   String permission,
            @NotNull    String command,
                        boolean onlyPlayer,
                        boolean onlyConsole,
            @Nullable   List<String> usage,
                        boolean tabComplete
    ) {
        super(aevyCore, plugin, permission, command, onlyPlayer, onlyConsole, usage, tabComplete);
    }

    @Override
    public boolean command(CommandSender sender, String[] args)
    {
        aevyCore.getConfiguration().reload();
        send.successMessage(sender, (String) config.getValue(DefaultEntries.RELOAD_MESSAGE));
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args)
    {
        return null;
    }

}

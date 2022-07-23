package fun.aevy.aevycore.commands;

import fun.aevy.aevycore.utils.builders.CommandsBuilder;
import fun.aevy.aevycore.utils.configuration.entries.AevyCoreEntries;
import fun.aevy.aevycore.utils.formatting.MessageProperties;
import fun.aevy.aevycore.utils.formatting.Send;
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
    private MessageProperties reloadMessage;

    /**
     * Constructor for new Commands, which they get automatically registered.
     *
     * @param plugin      The plugin that is using AevyCore as library.
     * @param send        Instance of Send.
     * @param permission  Permission required to run the command.
     * @param command     The command itself.
     * @param onlyPlayer  Makes the command runnable only to players.
     * @param onlyConsole Makes the command runnable only to players.
     * @param usage       How the command should be run.
     * @param tabComplete Enables tab complete.
     */
    public ReloadCommand(
            @NotNull    JavaPlugin      plugin,
            @NotNull    Send            send,
            @Nullable   String          permission,
            @NotNull    String          command,
                        boolean         onlyPlayer,
                        boolean         onlyConsole,
            @Nullable   List<String>    usage,
                        boolean         tabComplete
    ) {
        super(plugin, send, permission, command, onlyPlayer, onlyConsole, usage, tabComplete);
        reload();
    }

    @Override
    public boolean command(CommandSender sender, String[] args)
    {
        aevyCore.getConfiguration().reload();

        aevyCore.getReloadCommand().reload();
        aevyCore.getVersionCommand().reload();

        Send.message(sender, reloadMessage);
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args)
    {
        return null;
    }

    @Override
    public void reload()
    {
        reloadMessage = config.get(AevyCoreEntries.RELOAD_MESSAGE).getMessageProperties();
    }
}

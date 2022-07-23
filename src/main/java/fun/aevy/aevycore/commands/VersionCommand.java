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
 * The command /coreversion. Outputs the AevyCore's version.
 * @since 1.0
 * @author Sorridi
 */
public class VersionCommand extends CommandsBuilder
{
    private MessageProperties versionMessage;

    /**
     * Constructor for new Commands, which they get automatically registered.
     *
     * @param plugin      The plugin that is using AevyCore as library.
     * @param permission  Permission required to run the command.
     * @param command     The command itself.
     * @param onlyPlayer  Makes the command runnable only to players.
     * @param onlyConsole Makes the command runnable only to players.
     * @param usage       How the command should be run.
     * @param tabComplete Enables tab complete.
     */
    public VersionCommand(
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
        Send.message(sender, versionMessage);
        return true;
    }

    public List<String> tabComplete(String[] args)
    {
        return null;
    }

    @Override
    public void reload()
    {
        versionMessage = config.get(AevyCoreEntries.VERSION_MESSAGE).getMessageProperties();

        String[] toReplace      = new String[] { "{version}", "{site}" };
        String[] replacements   = new String[] { aevyCore.getVersion(), aevyCore.getSite() };

        versionMessage.replace(toReplace, replacements);
    }
}

package fun.aevy.aevycore.commands;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.utils.builders.CommandsBuilder;
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
    public VersionCommand(
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
        send.message(sender, "Version: "    + aevyCore.getVersion());
        send.message(sender, "Repo: "       + aevyCore.getSite());
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args)
    {
        return null;
    }

}

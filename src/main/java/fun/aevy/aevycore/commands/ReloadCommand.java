package fun.aevy.aevycore.commands;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.utils.builders.CommandsBuilder;
import fun.aevy.aevycore.utils.configuration.entries.AevyCoreEntries;
import fun.aevy.aevycore.utils.formatting.MessageProperties;
import fun.aevy.aevycore.utils.formatting.Send;
import org.bukkit.command.CommandSender;
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

    public ReloadCommand(@NotNull AevyCore aevyCore, @Nullable String permission, @NotNull String command)
    {
        super(aevyCore, permission, command);
    }

    @Override
    public boolean command(CommandSender sender, String[] args)
    {
        aevyCore.getCoolConfig().reload();

        aevyCore.getReloadCommand().reloadVars();
        aevyCore.getVersionCommand().reloadVars();

        Send.message(sender, reloadMessage);
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args)
    {
        return null;
    }

    @Override
    public void reloadVars()
    {
        reloadMessage = coolConfig.get(AevyCoreEntries.RELOAD_MESSAGE).getMessageProperties();
    }

}

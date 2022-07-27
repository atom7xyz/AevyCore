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
 * The command /coreversion. Outputs the AevyCore's version.
 * @since 1.0
 * @author Sorridi
 */
public class VersionCommand extends CommandsBuilder
{
    private MessageProperties versionMessage;

    public VersionCommand(@NotNull AevyCore aevyCore, @Nullable String permission, @NotNull String command)
    {
        super(aevyCore, permission, command);
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
    public void reloadVars()
    {
        versionMessage = coolConfig.get(AevyCoreEntries.VERSION_MESSAGE).getMessageProperties();

        String[] toReplace      = new String[] { "{version}", "{site}" };
        String[] replacements   = new String[] { aevyCore.getVersion(), aevyCore.getSite() };

        versionMessage.replace(toReplace, replacements);
    }
}

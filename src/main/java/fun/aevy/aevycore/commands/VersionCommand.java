package fun.aevy.aevycore.commands;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.utils.builders.CommandsBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class VersionCommand extends CommandsBuilder
{

    public VersionCommand(
            AevyCore        aevyCore,
            JavaPlugin      plugin,
            String          permission,
            String          command,
            boolean         onlyPlayer,
            boolean         onlyConsole,
            List<String>    usage,
            boolean         tabCompleteCustom
    ) {
        super(aevyCore, plugin, permission, command, onlyPlayer, onlyConsole, usage, tabCompleteCustom);
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

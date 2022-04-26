package fun.aevy.aevycore.commands;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.utils.builders.CommandsBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ReloadCommand extends CommandsBuilder
{

    public ReloadCommand(
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
        aevyCore.getConfiguration().reload();
        send.successMessage(sender, "Configurazione salvata e ricaricata!");
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args)
    {
        return null;
    }

}

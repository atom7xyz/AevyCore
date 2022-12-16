package fun.aevy.aevycore.commands;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.struct.elements.AevyDependent;
import fun.aevy.aevycore.struct.elements.events.ConfigReloadEvent;
import fun.aevy.aevycore.utils.VersioningUtils;
import fun.aevy.aevycore.utils.builders.CommandsBuilder;
import fun.aevy.aevycore.utils.configuration.entries.Aevy;
import fun.aevy.aevycore.utils.formatting.MessageProperties;
import fun.aevy.aevycore.utils.formatting.Send;
import lombok.val;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AevyCommand extends CommandsBuilder
{
    private String reloadPerm;

    private MessageProperties reload;
    private MessageProperties version;

    public AevyCommand(@NotNull AevyCore aevyCore, @NotNull String command)
    {
        super(aevyCore, command);
    }

    @Override
    public boolean command(CommandSender commandSender, String[] strings)
    {
        int args = strings.length;

        if (args == 1)
        {
            String firstArg = strings[0];

            switch (firstArg)
            {
                case "version":
                {
                    Send.message(commandSender, version);
                    break;
                }
                case "reload":
                {
                    if (commandSender.hasPermission(reloadPerm))
                    {
                        AevyDependent.callEvent(new ConfigReloadEvent(aevyDependent));
                        Send.message(commandSender, reload);
                    }
                    else
                    {
                        Send.message(commandSender, noPerms);
                    }
                    break;
                }
                default:
                    return false;
            }
            return true;
        }

        return false;
    }

    @Override
    public List<String> tabComplete(String[] strings)
    {
        if (strings.length == 1)
        {
            return List.of("version", "reload");
        }
        return null;
    }

    @Override
    public void reloadVars()
    {
        val versioning = new VersioningUtils(aevyCore);

        reloadPerm = (String) coolConfig.getValue(Aevy.Perms.RELOAD);

        reload  = coolConfig.getProperties(Aevy.CommandAevy.RELOAD);
        version = coolConfig.getProperties(Aevy.CommandAevy.VERSION);

        String ver  = versioning.getVersion();
        String hash = versioning.getHash();
        String site = versioning.getSite();
        val authors = versioning.getAuthors();

        val replace = new String[] { "{ver}", "{hash}", "{authors}", "{site}" };

        version.replace(replace, ver, hash, authors, site);
    }

}
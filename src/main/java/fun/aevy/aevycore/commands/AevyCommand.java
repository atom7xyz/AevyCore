package fun.aevy.aevycore.commands;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.struct.elements.events.ConfigReloadEvent;
import fun.aevy.aevycore.utils.builders.CommandsBuilder;
import fun.aevy.aevycore.utils.configuration.entries.Aevy;
import fun.aevy.aevycore.utils.formatting.MessageProperties;
import fun.aevy.aevycore.utils.formatting.Send;
import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Paths;
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
                        aevyCore.callEvent(new ConfigReloadEvent(aevyDependent));
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
        PluginDescriptionFile descriptionFile = aevyCore.getDescription();

        String plName   = descriptionFile.getName();
        String plVers   = descriptionFile.getVersion();
        String plSite   = descriptionFile.getWebsite();
        String fileName = plName + "-" + plVers + ".jar";
        String fileHash = "unknown hash";

        try
        {
            byte[] file = Files.readAllBytes(Paths.get("./plugins/" + fileName));
            fileHash = DigestUtils.md5Hex(file).toLowerCase().substring(0, 8);
        }
        catch (Exception ignored) { }

        setUsage(Aevy.Usages.AEVY);

        reload  = coolConfig.getProperties(Aevy.CommandAevy.RELOAD);
        version = coolConfig.getProperties(Aevy.CommandAevy.VERSION);

        version.replace(new String[] { "{ver}", "{hash}", "{site}" }, plVers, fileHash, plSite);

        reloadPerm = (String) coolConfig.getValue(Aevy.Perms.RELOAD);
    }

}

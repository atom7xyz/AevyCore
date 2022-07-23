package fun.aevy.aevycore.utils.formatting;

import fun.aevy.aevycore.utils.configuration.Config;
import fun.aevy.aevycore.utils.configuration.ConfigEntry;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Utility class used to send formatted messages to {@link CommandSender}s.
 * @since 1.5
 * @author Sorridi
 */
@AllArgsConstructor
public class Send
{
    private Config config;

    public void message(CommandSender sender, Enum<?> configEnum)
    {
        ConfigEntry configEntry = config.get(configEnum);

        if (configEntry.isText())
        {
            message(sender, configEntry.getMessageProperties());
        }
    }

    public static void message(CommandSender sender, MessageProperties properties)
    {
        String prefix   = properties.getPrefix();
        String message  = properties.getActualMessage();

        if (prefix == null)
        {
            sender.sendMessage(message);
        }
        else
        {
            sender.sendMessage(prefix + message);
        }
    }

    public static void broadcast(MessageProperties properties, boolean consoleToo)
    {
        Bukkit.getOnlinePlayers().forEach(player -> message(player, properties));

        if (consoleToo)
        {
            System.out.println(properties.getPrefix() + properties.getActualMessage());
        }
    }

}
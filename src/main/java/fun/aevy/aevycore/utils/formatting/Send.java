package fun.aevy.aevycore.utils.formatting;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import fun.aevy.aevycore.utils.configuration.elements.ConfigEntry;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Utility class used to send formatted messages to {@link CommandSender}s.
 * @since 1.5
 * @author Sorridi
 */
@AllArgsConstructor
public class Send
{
    private CoolConfig coolConfig;

    public void message(CommandSender sender, Enum<?> configEnum)
    {
        ConfigEntry configEntry = coolConfig.get(configEnum);

        if (configEntry.isText())
        {
            message(sender, configEntry.getMessageProperties());
        }
    }

    public static void message(CommandSender sender, MessageProperties properties)
    {
        String message = properties.getMessage();
        sender.sendMessage(message);
    }

    public static void broadcast(MessageProperties properties, boolean consoleToo)
    {
        Bukkit.getOnlinePlayers().forEach(player -> message(player, properties));

        if (consoleToo)
        {
            System.out.println(properties.getActualMessage());
        }
    }

    public static void actionBar(Player player, MessageProperties properties)
    {
        ActionBarAPI.sendActionBar(player, properties.getMessage());
    }

    public static void actionBar(Player player, MessageProperties properties, int duration)
    {
        ActionBarAPI.sendActionBar(player, properties.getMessage(), duration);
    }

    public static void actionBarToAll(MessageProperties properties)
    {
        ActionBarAPI.sendActionBarToAllPlayers(properties.getMessage());
    }

    public static void actionBarToAll(MessageProperties properties, int duration)
    {
        ActionBarAPI.sendActionBarToAllPlayers(properties.getMessage(), duration);
    }

    private static boolean isHuman(CommandSender sender)
    {
        return sender instanceof Player;
    }

}
package fun.aevy.aevycore.utils.formatting;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import fun.aevy.aevycore.utils.configuration.elements.ConfigEntry;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

/**
 * Utility class used to send formatted messages to {@link CommandSender}s.
 * @since 1.5
 * @author Sorridi
 */
@AllArgsConstructor
public class Send
{
    private CoolConfig coolConfig;

    /**
     * Sends a message to a {@link CommandSender}.
     * @param target        The target.
     * @param configEnum    The config entry.
     * @param <T>           The type of the config entry.
     */
    public <T extends CommandSender> void message(T target, Enum<?> configEnum)
    {
        ConfigEntry configEntry = coolConfig.get(configEnum);

        if (configEntry.isText())
        {
            message(target, configEntry.getMessageProperties());
        }
    }

    /**
     * Sends a message to a {@link CommandSender}.
     * @param target        The target.
     * @param properties    The message properties.
     * @param <T>           The type of the target.
     */
    public static <T extends CommandSender> void message(T target, MessageProperties properties)
    {
        String message = properties.getMessage();

        if (isHuman(target) && !((Player) target).isOnline())
        {
            return;
        }

        switch (properties.getType())
        {
            case STRING:
            {
                target.sendMessage(message);
                break;
            }
            case LIST:
            {
                properties.getMessages().forEach(target::sendMessage);
            }
        }
    }

    /**
     * Broadcasts a message to all players.
     * @param properties    The message to broadcast.
     * @param consoleToo    Whether to send the message to the console too.
     */
    public static void broadcast(MessageProperties properties, boolean consoleToo)
    {
        Bukkit.getOnlinePlayers().forEach(player -> message(player, properties));

        if (consoleToo)
        {
            System.out.println(properties.getActualMessage());
        }
    }

    /**
     * Broadcasts a message to all players.
     * @param properties    The message to broadcast.
     * @param except        The player to exclude from the broadcast.
     * @param consoleToo    Whether to send the message to the console too.
     */
    public static void broadcast(MessageProperties properties, Player except, boolean consoleToo)
    {
        Bukkit.getOnlinePlayers()
                .stream()
                .filter(player -> !player.equals(except))
                .forEach(player -> message(player, properties));

        if (consoleToo)
        {
            System.out.println(properties.getActualMessage());
        }
    }

    /**
     * Sends the action bar to a player.
     * @param player        The player to send the action bar to.
     * @param properties    The properties of the action bar.
     */
    public static void actionBar(Player player, MessageProperties properties)
    {
        ActionBarAPI.sendActionBar(player, properties.getMessage());
    }

    /**
     * Sends the action bar to a player.
     * @param player        The player to send the action bar to.
     * @param properties    The properties of the message.
     * @param duration      The duration of the action bar.
     */
    public static void actionBar(Player player, MessageProperties properties, int duration)
    {
        ActionBarAPI.sendActionBar(player, properties.getMessage(), duration);
    }

    /**
     * Sends the action bar to all players.
     * @param properties The message properties.
     */
    public static void actionBarToAll(MessageProperties properties)
    {
        ActionBarAPI.sendActionBarToAllPlayers(properties.getMessage());
    }

    /**
     * Sends the action bar to all players.
     * @param properties    The message properties.
     * @param duration      The duration of the action bar.
     */
    public static void actionBarToAll(MessageProperties properties, int duration)
    {
        ActionBarAPI.sendActionBarToAllPlayers(properties.getMessage(), duration);
    }

    /**
     * Checks if a {@link CommandSender} is human.
     * @param sender The {@link CommandSender} to check.
     * @return {@code true} if the {@link CommandSender} is human.
     */
    private static boolean isHuman(CommandSender sender)
    {
        return sender instanceof Player;
    }

    /**
     * Sends an error message to the console.
     * @param logger    The logger to use.
     * @param message   Error message to send.
     * @param args      The arguments to use.
     */
    public static void errorMessage(Logger logger, String message, Object ...args)
    {
        logger.severe(String.format(message, args));
    }

    /**
     * Sends a warning message to the console.
     * @param logger    Logger to use.
     * @param message   Warning message to send.
     * @param args      Arguments to use.
     */
    public static void warningMessage(Logger logger, String message, Object ...args)
    {
        logger.warning(String.format(message, args));
    }

    /**
     * Sends an info message to the console.
     * @param logger    Logger to use.
     * @param message   Info message to send.
     * @param args      Arguments to use.
     */
    public static void infoMessage(Logger logger, String message, Object ...args)
    {
        logger.info(String.format(message, args));
    }

}
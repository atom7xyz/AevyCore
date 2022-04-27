package fun.aevy.aevycore.utils.strings;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.utils.configuration.Config;
import fun.aevy.aevycore.utils.configuration.entries.DefaultEntries;
import fun.aevy.aevycore.utils.formatting.MessageResult;
import org.bukkit.ChatColor;

/**
 * Utility class used to format and color messages.
 * @since 1.0
 * @author Sorridi
 */
public class StringUtils
{
    private final Config config;

    /**
     * Constructor for StringUtils.
     * @param aevyCore Instance of AevyCore.
     */
    public StringUtils(AevyCore aevyCore)
    {
        config = aevyCore.getConfiguration();
    }

    /**
     * Formats a message.
     * @param message       The message that needs to be formatted.
     * @param messageResult Typology of the message.
     * @return The formatted message.
     */
    public String formatMessage(String message, MessageResult messageResult)
    {
        return color(config.getValue(DefaultEntries.PREFIX) + stringFromResult(messageResult) + message);
    }

    /**
     * Retrieves the color of the messageResult from the config.
     * @param messageResult Typology of the message.
     * @return Color of the messageResult.
     */
    public String stringFromResult(MessageResult messageResult)
    {
        return (String) (messageResult == MessageResult.SUCCESS ? config.getValue(DefaultEntries.SUCCESS):
                        messageResult == MessageResult.NORMAL   ? config.getValue(DefaultEntries.NORMAL) :
                        config.getValue(DefaultEntries.ERROR));
    }

    /**
     * Colors the message.
     * @param message Message to be colored.
     * @return The colored message.
     */
    public String color(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}

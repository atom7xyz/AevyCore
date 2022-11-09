package fun.aevy.aevycore.utils;

import org.bukkit.ChatColor;

/**
 * Utility class used to format and color messages.
 * @since 1.0
 * @author Sorridi
 */
public class Shortcuts
{

    /**
     * Colors the message.
     * @param message Message to be colored and formatted.
     * @return The colored and formatted message.
     */
    public static String color(String message, Object ...args)
    {
        return ChatColor.translateAlternateColorCodes('&', String.format(message, args));
    }

}

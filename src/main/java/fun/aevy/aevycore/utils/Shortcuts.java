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
     * @param message Message to be colored.
     * @return The colored message.
     */
    public static String color(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}

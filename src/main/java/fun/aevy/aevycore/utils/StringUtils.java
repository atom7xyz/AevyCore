package fun.aevy.aevycore.utils;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Utility class used to format Strings.
 * @since 1.6
 * @author Sorridi, Niketion
 */
public class StringUtils
{
    private static final DecimalFormat singleDigitFormat  = new DecimalFormat("0.0");
    private static final DecimalFormat doubleDigitFormat  = new DecimalFormat("0.00");

    /**
     * Converts minutes in String, using HH:mm format.
     * @param minutes The minutes.
     * @return The formatted String.
     */
    public static String fromMinutesToHHmm(int minutes)
    {
        long hours          = TimeUnit.MINUTES.toHours(minutes);
        long hoursAsMinutes = TimeUnit.HOURS.toMinutes(hours);
        long remainMinutes  = minutes - hoursAsMinutes;

        return String.format("%02dh %02dm", hours, remainMinutes);
    }

    /**
     * Strips colors from a String.
     * @param string The string.
     * @return The string without colors.
     */
    public static String stripColor(String string)
    {
        return ChatColor.stripColor(string);
    }

    /**
     * Extracts a number from a String.
     * @param string The string to extract the number from.
     * @return The value extracted.
     */
    public static long extractNumber(String string)
    {
        int temp = 0;
        
        try
        {
            temp = Integer.parseInt(stripColor(string).replaceAll("[^0-9]", ""));
        }
        catch (NumberFormatException ignored) { }

        return temp;
    }

    /**
     * Rappresents a number in the Roman form.
     * @param input The number to be converted.
     * @return The roman equivalent of the number.
     */
    public static String toRoman(int input) 
    {
        StringBuilder temp = new StringBuilder();

        while (input >= 1000)
        {
            temp.append("M");
            input -= 1000;
        } 
        while (input >= 900)
        {
            temp.append("CM");
            input -= 900;
        } 
        while (input >= 500)
        {
            temp.append("D");
            input -= 500;
        } 
        while (input >= 400)
        {
            temp.append("CD");
            input -= 400;
        } 
        while (input >= 100) 
        {
            temp.append("C");
            input -= 100;
        }
        while (input >= 90) 
        {
            temp.append("XC");
            input -= 90;
        } 
        while (input >= 50) 
        {
            temp.append("L");
            input -= 50;
        }
        while (input >= 40) 
        {
            temp.append("XL");
            input -= 40;
        }
        while (input >= 10) 
        {
            temp.append("X");
            input -= 10;
        } 
        while (input >= 9) 
        {
            temp.append("IX");
            input -= 9;
        }
        while (input >= 5)
        {
            temp.append("V");
            input -= 5;
        }
        while (input >= 4)
        {
            temp.append("IV");
            input -= 4;
        } 
        while (input >= 1)
        {
            temp.append("I");
            input -= 1;
        }

        return temp.toString();
    }

    /**
     * Converts the time into a String, using HH:mm:ss dd/MM/yy format.
     */
    public static String convertCurrentTime()
    {
        Date date       = new Date(System.currentTimeMillis());
        Format format   = new SimpleDateFormat("HH:mm:ss dd/MM/yy");

        return format.format(date);
    }

    /**
     * Converts the time into a String, using HH:mm:ss dd/MM/yy format.
     */
    public static String convertTime(long time)
    {
        Date date       = new Date(time);
        Format format   = new SimpleDateFormat("HH:mm:ss dd/MM/yy");

        return format.format(date);
    }

    /**
     * Checks if a String is a number.
     * @param string The String to be checked.
     * @return If the String is a number (true) or not (false).
     */
    public static boolean isNumber(String string)
    {
        return NumberUtils.isNumber(string);
    }

    /**
     * Converts a double into a String, using the format 0.0.
     * @param value The value to be converted.
     * @return The converted value.
     */
    public static String formatSingle(double value)
    {
        return singleDigitFormat.format(value);
    }

    /**
     * Converts a double into a String, using the format 0.00.
     * @param value The value to be converted.
     * @return The converted value.
     */
    public static String formatDouble(double value)
    {
        return doubleDigitFormat.format(value);
    }

    /**
     * Converts a double into a String, using the format 0.0...
     * @param value The value to be converted.
     * @param decimals The number of decimals.
     * @return The converted value.
     */
    public static String format(double value, int decimals)
    {
        StringBuilder format = new StringBuilder("0");

        if (decimals > 0)
        {
            format.append(".").append("0".repeat(decimals));
        }

        return new DecimalFormat(format.toString()).format(value);
    }

}

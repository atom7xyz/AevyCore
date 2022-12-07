package fun.aevy.aevycore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Utility class used to format {@link Location}s as Strings and vice-versa.
 * @since 1.6
 * @author Sorridi, Niketion
 */
public class LocationUtils
{
    /**
     * Checks if two locations are equal in terms of X, Y, Z (ignoring yaw and pitch).
     * @param location  Location to compare.
     * @param toCompare Location to compare.
     * @return If the locations are the same (true) or not (false).
     */
    public static boolean isEqual(Location location, Location toCompare)
    {
        if (isLocationNull(location) || isLocationNull(toCompare))
        {
            return false;
        }

        World world     = location.getWorld();
        World _world    = toCompare.getWorld();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        double _x = toCompare.getX();
        double _y = toCompare.getY();
        double _z = toCompare.getZ();

        return (world == _world) && (x == _x) && (y == _y) && (z == _z);
    }

    /**
     * Checks if a location is null.
     * @param location  The location to check.
     * @return If the location is null (true) or not (false).
     */
    public static boolean isLocationNull(Location location)
    {
        return location == null;
    }

    /**
     * Extracts the X, Y, Z of a location in a String, using X:Y:Z format.
     * @param location The location to extract from.
     * @return The extracted string.
     */
    public static String extractStringPrecise(Location location)
    {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        return concat(x, y, z);
    }

    /**
     * Extracts the X, Y, Z of a location in a String, using X:Y:Z format.
     * @param location The location to extract from.
     * @return The extracted string.
     */
    public static String extractString(Location location)
    {
        int x = (int) location.getX();
        int y = (int) location.getY();
        int z = (int) location.getZ();

        return concat(x, y, z);
    }

    /**
     * Converts the location into a String, using WORLD:X:Y:Z:YAW:PITCH format.
     * @param location The location to convert from.
     * @return The location converted.
     */
    public static String getStringFromLocation(Location location)
    {
        if (isLocationNull(location))
        {
            return "";
        }

        String worldName = location.getWorld().getName();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        float yaw   = location.getYaw();
        float pitch = location.getPitch();

        return concat(worldName, x, y, z, yaw, pitch);
    }

    /**
     * Concatenates objects in a OBJ:OBJ format.
     * @param object The objects to be concatenated.
     * @return The contatenated String.
     */
    private static String concat(Object... object)
    {
        StringBuilder temp  = new StringBuilder();
        int objectsNum      = object.length;

        for (int i = 0; i < objectsNum; i++)
        {
            temp.append(object[i]);

            if (i + 1 != objectsNum)
            {
                temp.append(":");
            }
        }

        return temp.toString();
    }

    /**
     * Gets the location from a String, using WORLD:X:Y:Z:YAW:PITCH format, YAW and PITCH are optional.
     * @param stringLocation    The string to get the location from.
     * @param full              If YAW and PITCH are required (true) or not (false).
     * @return The location obtained from the String.
     */
    public static Location getLocationFromString(String stringLocation, boolean full)
    {
        Location location;

        if (stringLocation == null)
        {
            return null;
        }

        String[] parts = stringLocation.split(":");

        World world = Bukkit.getServer().getWorld(parts[0]);
        double x    = Double.parseDouble(parts[1]);
        double y    = Double.parseDouble(parts[2]);
        double z    = Double.parseDouble(parts[3]);

        if (full)
        {
            float yaw   = Float.parseFloat(parts[4]);
            float pitch = Float.parseFloat(parts[5]);

            location = new Location(world, x, y, z, yaw, pitch);
        }
        else
        {
            location = new Location(world, x, y, z);
        }

        return location;
    }

    /**
     * Gets the middle of a location.
     * @param location  The location to get the middle from.
     * @param yawPitch  If the yaw and pitch should be the same as the original location (true) or not (false).
     * @return The middle of the location.
     */
    public static Location getMiddleLocation(Location location, boolean yawPitch)
    {
        World world = location.getWorld();

        double x = (int) location.getX() + .5;
        double y = location.getY();
        double z = (int) location.getZ() + .5;

        if (yawPitch)
        {
            float yaw   = location.getYaw();
            float pitch = location.getPitch();

            return new Location(world, x, y, z, yaw, pitch);
        }
        else
        {
            return new Location(world, x, y, z);
        }
    }

}

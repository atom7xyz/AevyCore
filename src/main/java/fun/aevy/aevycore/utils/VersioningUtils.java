package fun.aevy.aevycore.utils;

import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility class used to get information about the plugin.
 * @author Sorridi
 * @since 1.8
 */
@Getter
public class VersioningUtils
{
    private final Plugin plugin;

    private final List<String>  rawAuthors, depends, softDepends;
    private final String        name, version, site, description, hash, authors;

    public VersioningUtils(@NotNull Plugin plugin)
    {
        this.plugin = plugin;
        name        = getDescription().getName();
        version     = getDescription().getVersion();
        rawAuthors  = getDescription().getAuthors();
        authors     = getFormattedAuthors();
        site        = getDescription().getWebsite();
        description = getDescription().getDescription();
        depends     = getDescription().getDepend();
        softDepends = getDescription().getSoftDepend();
        hash        = getHash(8);
    }

    /**
     * Gets the formatted authors of the plugin.
     * @return The formatted authors of the plugin.
     */
    private String getFormattedAuthors()
    {
        return String.valueOf(rawAuthors).replace("[", "").replace("]", "");
    }

    /**
     * Gets the plugin description file.
     * @return The plugin description file.
     */
    public PluginDescriptionFile getDescription()
    {
        return plugin.getDescription();
    }

    /**
     * Gets the hash of the plugin.
     * @param length The length of the hash.
     * @return The hash of the plugin.
     */
    public String getHash(int length)
    {
        String fileName = name + "-" + version + ".jar";
        String fileHash = "unknown hash";

        try
        {
            byte[] file = Files.readAllBytes(Paths.get("./plugins/" + fileName));
            fileHash = DigestUtils.md5Hex(file).toLowerCase().substring(0, length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return fileHash;
    }

}
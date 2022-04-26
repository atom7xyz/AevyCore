package fun.aevy.aevycore.utils.configuration;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

@Getter
public class Config
{
    protected final JavaPlugin  javaPlugin;
    protected FileConfiguration fileConfiguration;

    protected final HashMap<Enum<?>, ConfigEntry> entries;

    /**
     * Creates a new empty config.
     * @param javaPlugin Instance of the plugin.
     * @param configType The type of the config.
     * @since 1.0
     */
    public Config(JavaPlugin javaPlugin, ConfigType configType)
    {
        this.javaPlugin     = javaPlugin;
        entries             = new HashMap<>();
        fileConfiguration   = null;

        if (configType == ConfigType.DEFAULT)
        {
            create();
            fileConfiguration = javaPlugin.getConfig();
        }
    }

    /**
     * Retrieves the {@link ConfigEntry} correspondent to the Enum.
     * @param e The enum to be searched.
     * @return  The enum's value.
     * @since 1.0
     */
    public ConfigEntry get(Enum<?> e)
    {
        return entries.get(e);
    }

    /**
     * Retrieves the value of the {@link ConfigEntry} correspondent to the Enum
     * @param e The enum to be searched.
     * @return  The {@link ConfigEntry#getValue()}.
     * @since 1.0
     */
    public Object getValue(Enum<?> e)
    {
        return get(e).getValue();
    }

    /**
     * Retrieves the path of the {@link ConfigEntry} correspondent to the Enum
     * @param e The enum to be searched.
     * @return  The {@link ConfigEntry#getPath()}.
     * @since 1.0
     */
    public String getPath(Enum<?> e)
    {
        return get(e).getPath();
    }

    /**
     * Assembles part of the given path with the Enum's name, then it creates and puts a new entry
     * in the HashMap.
     * @param e     The enum to be added.
     * @param path  The path of the new {@link ConfigEntry}.
     * @since 1.0
     */
    public void add(Enum<?> e, String path)
    {
        String newPath = path + "." + e.name();
        entries.put(e, new ConfigEntry(newPath, fileConfiguration.get(newPath)));
    }

    /**
     * Removes the entry which key corresponds to the Enum.
     * @param e The enum to be searched.
     * @since 1.0
     */
    public void remove(Enum<?> e)
    {
        entries.remove(e);
    }

    /**
     * Replaces the {@link ConfigEntry} correspondent to the Enum with another one.
     * @param e             The enum to be searched.
     * @param configEntry   The new {@link ConfigEntry}.
     * @since 1.0
     */
    public void replace(Enum<?> e, ConfigEntry configEntry)
    {
        entries.replace(e, configEntry);
    }

    /**
     * Creates the default configuration file using {@link JavaPlugin#saveDefaultConfig()}
     * @since 1.0
     */
    public void create()
    {
        javaPlugin.saveDefaultConfig();
    }

    /**
     * Saves the configuration file using {@link JavaPlugin#saveConfig()}
     * @since 1.0
     */
    public void save()
    {
        javaPlugin.saveConfig();
    }

    /**
     * Reloads the configuration file and loads the data in RAM
     * using {@link JavaPlugin#reloadConfig()} and {@link #loadData()}
     * @since 1.1
     */
    public void reload()
    {
        javaPlugin.reloadConfig();
        fileConfiguration = javaPlugin.getConfig();
        loadData();
    }

    /**
     * Updates the values of the in RAM config's entries.
     * @since 1.1
     */
    public void loadData()
    {
        HashMap<Enum<?>, ConfigEntry> temp = new HashMap<>(entries);

        temp.forEach((anEnum, configEntry) ->
        {
            String path     = configEntry.getPath();
            Object value    = fileConfiguration.get(path);

            replace(anEnum, new ConfigEntry(path, value));
        });
    }

}

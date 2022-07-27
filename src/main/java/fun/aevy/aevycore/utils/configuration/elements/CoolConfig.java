package fun.aevy.aevycore.utils.configuration.elements;

import fun.aevy.aevycore.utils.formatting.MessageProperties;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

/**
 * Creates a new configuration manager. An empty HashMap is automatically provided.
 * @since 1.0
 * @author Sorridi
 */
@Getter
public class CoolConfig
{
    protected final JavaPlugin  javaPlugin;
    protected FileConfiguration fileConfiguration;

    protected final HashMap<Enum<?>, ConfigEntry> entries;

    /**
     * Creates a new empty coolConfig.
     * @param javaPlugin Instance of the plugin.
     * @param configType The type of the coolConfig.
     * @since 1.0
     */
    public CoolConfig(JavaPlugin javaPlugin, ConfigType configType)
    {
        this.javaPlugin     = javaPlugin;
        entries             = new HashMap<>();
        fileConfiguration   = null;

        if (configType == ConfigType.DEFAULT)
        {
            create();
            fileConfiguration = javaPlugin.getConfig();

            if (fileConfiguration == null)
            {
                File file = new File(javaPlugin.getDataFolder(), "config.yml");
                fileConfiguration = YamlConfiguration.loadConfiguration(file);
            }
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
        String prefix   = (String) fileConfiguration.get("messages.PREFIX");
        String newPath  = path + "." + e.name();
        Object value    = fileConfiguration.get(newPath);

        if (value instanceof String)
        {
            MessageProperties properties = new MessageProperties((String) value, prefix);
            entries.put(e, new ConfigEntry(newPath, value, properties, true));
        }
        else
        {
            entries.put(e, new ConfigEntry(newPath, value, null, false));
        }
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
     * Updates the values of the in RAM coolConfig's entries.
     * @since 1.1
     */
    public void loadData()
    {
        HashMap<Enum<?>, ConfigEntry> temp = new HashMap<>(entries);

        String prefix = (String) fileConfiguration.get("messages.PREFIX");

        temp.forEach((anEnum, configEntry) ->
        {
            String path     = configEntry.getPath();
            Object value    = fileConfiguration.get(path);

            if (value instanceof String)
            {
                MessageProperties properties = new MessageProperties((String) value, prefix);
                replace(anEnum, new ConfigEntry(path, value, properties, true));
            }
            else
            {
                replace(anEnum, new ConfigEntry(path, value, null, false));
            }
        });
    }

    /**
     * Sets a value in the plugin's configuration.
     * @param path      The path to the section to replace.
     * @param object    The object to set in the section.
     */
    public void setSpigotConfig(String path, Object object)
    {
        fileConfiguration.set(path, object);
    }

}

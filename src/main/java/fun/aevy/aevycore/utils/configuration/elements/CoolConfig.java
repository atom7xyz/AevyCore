package fun.aevy.aevycore.utils.configuration.elements;

import fun.aevy.aevycore.utils.AnnotationUtils;
import fun.aevy.aevycore.utils.configuration.elements.annotations.Resource;
import fun.aevy.aevycore.utils.formatting.MessageProperties;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Creates a new configuration manager. An empty HashMap is automatically provided.
 * @since 1.0
 * @author Sorridi
 */
@Getter
public class CoolConfig
{
    protected final JavaPlugin  javaPlugin;
    protected final Logger      logger;
    protected FileConfiguration fileConfiguration;

    protected HashMap<Enum<?>, ConfigEntry> entries;

    /**
     * Creates a new empty coolConfig.
     * @param javaPlugin Instance of the plugin.
     * @param configType The type of the coolConfig.
     * @since 1.0
     */
    public CoolConfig(JavaPlugin javaPlugin, ConfigType configType)
    {
        this.javaPlugin     = javaPlugin;
        logger              = javaPlugin.getLogger();
        entries             = new HashMap<>();
        fileConfiguration   = null;

        if (configType == ConfigType.DEFAULT)
        {
            create();
            fileConfiguration = javaPlugin.getConfig();

            if (fileConfiguration == null)
            {
                File file           = new File(javaPlugin.getDataFolder(), "config.yml");
                fileConfiguration   = YamlConfiguration.loadConfiguration(file);
            }
        }

        addResources(javaPlugin);
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
     * Retrieves the value of the {@link ConfigEntry} correspondent to the Enum
     * @param e             The enum to be searched.
     * @param defaultValue  The default value.
     * @return The {@link ConfigEntry#getValue()}.
     * @since 1.0
     */
    public Object getValue(Enum<?> e, Object defaultValue)
    {
        Object object = getValue(e);

        if (object instanceof Integer)
        {
            return (int) object < (int) defaultValue ? defaultValue : object;
        }

        if (object instanceof Double)
        {
            return (double) object < (double) defaultValue ? defaultValue : object;
        }

        if (object instanceof Float)
        {
            return (float) object < (float) defaultValue ? defaultValue : object;
        }

        if (object instanceof Short)
        {
            return (short) object < (short) defaultValue ? defaultValue : object;
        }

        if (object instanceof String)
        {
            return ((String) object).isEmpty() ? defaultValue : object;
        }

        return object;
    }

    /**
     * Retrieves the path of the {@link ConfigEntry} correspondent to the Enum
     * @param e The enum to be searched.
     * @return The {@link ConfigEntry#getPath()}.
     * @since 1.0
     */
    public String getPath(Enum<?> e)
    {
        return get(e).getPath();
    }

    public void addResources(JavaPlugin javaPlugin)
    {
        AnnotationUtils.Action action = (annotation, classFound) ->
        {
            Resource resource = (Resource) annotation;

            for (Object object : classFound.getEnumConstants())
            {
                if (!(object instanceof Enum))
                {
                    continue;
                }

                add((Enum<?>) object, resource.path());
                logger.info(String.format("Added new entry of %s (%s)", object, resource.path()));
            }
        };

        logger.info(String.format("Scanning with %s", javaPlugin.getClass()));

        AnnotationUtils.forEachAnnotation(Resource.class, action, javaPlugin);
    }

    /**
     * Assembles part of the given path with the Enum's name, then it creates and puts a new entry
     * in the HashMap.
     * @param e         The enum to be added.
     * @param tempPath  The path in the config.
     * @since 1.0
     */
    public void add(Enum<?> e, String tempPath)
    {
        String prefix   = getCurrentPrefix();
        String newPath  = tempPath + "." + e.name();
        Object value    = fileConfiguration.get(newPath);

        boolean entryWithPrefix         = entryHasPrefix(value);
        MessageProperties properties    = null;
        ConfigEntry newEntry;

        if (value instanceof String)
        {
            properties  = new MessageProperties((String) value);
            newEntry    = new ConfigEntry(tempPath, value, properties, true);
        }
        else if (value instanceof ArrayList)
        {
            properties  = new MessageProperties((List<String>) value);
            newEntry    = new ConfigEntry(tempPath, value, properties, true);
        }
        else
        {
            newEntry = new ConfigEntry(tempPath, value, null, false);
        }

        if (properties != null)
        {
            if (entryWithPrefix)
            {
                properties.withPrefix(prefix);
            }
            else
            {
                properties.noPrefix();
            }
        }

        entries.put(e, newEntry);
    }

    private boolean entryHasPrefix(Object object)
    {
         String prefix = "{prefix}";

         if (object instanceof String)
         {
             return ((String) object).startsWith(prefix);
         }
         else if (object instanceof ArrayList)
         {
             return ((ArrayList<?>) object).stream().anyMatch(o -> ((String) o).startsWith(prefix));
         }
         return false;
    }

    /**
     * Gets messages.PREFIX from config.
     * @return The current prefix.
     */
    private String getCurrentPrefix()
    {
        return fileConfiguration.getString("messages.PREFIX");
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
        HashMap<Enum<?>, ConfigEntry> temp = new HashMap<>();

        String prefix = getCurrentPrefix();

        entries.forEach((e, configEntry) ->
        {
            String path     = getPath(e);
            Object value    = fileConfiguration.get(path + "." + e);

            boolean entryWithPrefix         = entryHasPrefix(value);
            MessageProperties properties    = null;

            ConfigEntry newEntry;

            if (value instanceof String)
            {
                properties  = new MessageProperties((String) value);
                newEntry    = new ConfigEntry(path, value, properties, true);
            }
            else if (value instanceof ArrayList)
            {
                properties  = new MessageProperties((List<String>) value);
                newEntry    = new ConfigEntry(path, value, properties, true);
            }
            else
            {
                newEntry = new ConfigEntry(path, value, null, false);
            }

            if (properties != null)
            {
                if (entryWithPrefix)
                {
                    properties.withPrefix(prefix);
                }
                else
                {
                    properties.noPrefix();
                }
            }

            temp.put(e, newEntry);
        });

        entries = temp;
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

    /**
     * Shortcut for getting the {@link MessageProperties}.
     * @param e The key of the entry.
     * @return The properties of the message.
     */
    public MessageProperties getProperties(Enum<?> e)
    {
        return get(e).getMessageProperties();
    }

}
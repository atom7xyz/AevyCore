package fun.aevy.aevycore.utils.configuration;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Creates a new custom configuration manager. [INCOMPLETE]
 * @author Sorridi
 */
@Deprecated
@Getter
public class ConfigCustom extends Config
{
    /* TODO This class is incomplete, needs to be worked on. */

    private final File file, dataFolder;

    public ConfigCustom(JavaPlugin javaPlugin, ConfigType configType, String fileName)
    {
        super(javaPlugin, configType);

        dataFolder  = javaPlugin.getDataFolder();
        file        = new File(dataFolder, fileName);

        create();
        loadData();
    }

    @Override
    public void create()
    {
        if (!file.exists())
        {
            file.getParentFile().mkdirs();
            javaPlugin.saveResource(file.getName(), false);
        }

        fileConfiguration = new YamlConfiguration();
        loadFile();
    }

    public void saveFile()
    {
        try {
            fileConfiguration.save(file);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadFile()
    {
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void reload()
    {
        saveFile();
        create();
        loadData();
    }

}

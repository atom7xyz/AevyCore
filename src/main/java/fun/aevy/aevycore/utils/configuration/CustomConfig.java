package fun.aevy.aevycore.utils.configuration;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@Getter
public class CustomConfig extends Config
{
    private final File file, dataFolder;

    public CustomConfig(JavaPlugin javaPlugin, ConfigType configType, String fileName)
    {
        super(javaPlugin, configType);

        dataFolder  = javaPlugin.getDataFolder();
        file        = new File(dataFolder, fileName);
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

        load();
    }

    @Override
    public void save()
    {
        try {
            fileConfiguration.save(file);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void reload()
    {
        save();
        load();
    }

    private void load()
    {
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }

}

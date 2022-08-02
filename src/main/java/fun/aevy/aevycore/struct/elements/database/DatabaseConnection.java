package fun.aevy.aevycore.struct.elements.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
import fun.aevy.aevycore.utils.configuration.entries.Aevy;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class DatabaseConnection
{
    private DataSource dataSource;

    private final String    ip, port, database, user, password, url, driver;
    private final boolean   useDefaults;
    private final int       poolSize;

    private final Map<String, String> properties;

    public DatabaseConnection(
            @NotNull    String              ip,
            @NotNull    String              port,
            @NotNull    String              database,
            @NotNull    String              user,
            @Nullable   String              password,
            @NotNull    String              url,
            @NotNull    String              driver,
            @Nullable   Map<String, String> properties,
                        boolean             useDefaults,
                        int                 poolSize
    ) {
        this.ip             = ip;
        this.port           = port;
        this.database       = database;
        this.user           = user;
        this.password       = password;
        this.url            = url;
        this.driver         = driver;
        this.properties     = properties;
        this.useDefaults    = useDefaults;
        this.poolSize       = poolSize;

        initialize();
    }

    public DatabaseConnection(CoolConfig coolConfig)
    {
        this.ip             = (String) coolConfig.getValue(Aevy.Database.IP);
        this.port           = (String) coolConfig.getValue(Aevy.Database.PORT);
        this.database       = (String) coolConfig.getValue(Aevy.Database.DATABASE);
        this.user           = (String) coolConfig.getValue(Aevy.Database.USER);
        this.password       = (String) coolConfig.getValue(Aevy.Database.PASSWORD);
        this.url            = (String) coolConfig.getValue(Aevy.Database.URL);
        this.driver         = (String) coolConfig.getValue(Aevy.Database.DRIVER);
        this.properties     = new HashMap<>();
        this.useDefaults    = (boolean) coolConfig.getValue(Aevy.Database.USE_DEFAULTS);
        this.poolSize       = (int) coolConfig.getValue(Aevy.Database.MAX_POOL_SIZE);
        List<String> list   = (List<String>) coolConfig.getValue(Aevy.Database.PROPERTIES);

        for (String s : list)
        {
            String[] split = s.split(" ");
            properties.put(split[0], split[1]);
        }

        initialize();
    }

    private void initialize()
    {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl("jdbc:" + url + "://" + ip + ":" + port + "/" + database);

        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);

        if (useDefaults)
        {
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        }

        hikariConfig.setMaximumPoolSize(poolSize == 0 ? Runtime.getRuntime().availableProcessors() * 2 : poolSize);

        properties.forEach(hikariConfig::addDataSourceProperty);

        dataSource = new HikariDataSource(hikariConfig);
    }

    @SafeVarargs
    @SneakyThrows
    public final <G extends AutoCloseable> void close(@Nullable G... connections)
    {
        for (G connection : connections)
        {
            if (connection != null)
            {
                connection.close();
            }
        }
    }

}

package fun.aevy.aevycore.struct.elements.database;

import fun.aevy.aevycore.struct.manager.DatabasesManager;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@SuppressWarnings({"BusyWait", "unused"})
@Getter
public class Database extends Thread
{
    private final JavaPlugin    javaPlugin;
    private final Logger        logger;

    private final DatabasesManager              databasesManager;
    @Setter
    private DatabaseConnection                  databaseConnection;
    @Setter
    private BlockingQueue<DatabaseOperation>    databaseOperations;

    private boolean debug, running, locked;
    private long    timeToWait;
    private int     opsCounter;

    public Database(
                        JavaPlugin          javaPlugin,
            @NotNull    DatabasesManager    databasesManager,
            @NotNull    DatabaseConnection  databaseConnection
    ) {
        this.javaPlugin = javaPlugin;
        this.logger     = javaPlugin.getLogger();

        this.debug              = false;
        this.timeToWait         = 100;
        this.databasesManager   = databasesManager;

        this.databaseOperations = new LinkedBlockingQueue<>();
        this.databaseConnection = databaseConnection;

        databasesManager.addDatabase(this);

        setName("Aevy-Database #" + databasesManager.getDatabasesNum());
        this.running = true;
        start();
    }

    public Database with(int timeToWait, TimeUnit timeUnit)
    {
        this.timeToWait = timeUnit.toMillis(timeToWait);
        return this;
    }

    public void add(@Nullable DatabaseOperation databaseOperation)
    {
        if (databaseOperation == null)
            return;

        databaseOperations.add(databaseOperation);
    }

    public Database debug(boolean value)
    {
        debug = value;
        return this;
    }

    public void stopRunning()
    {
        running = false;
    }

    @SneakyThrows
    @Override
    public void run()
    {
        long time;

        while (running)
        {
            if (isEmptyQueue())
            {
                Thread.sleep(timeToWait);
                continue;
            }
            time = System.currentTimeMillis();

            runOps();

            if (debug)
            {
                String timing   = "time = " + (System.currentTimeMillis() - time) + "ms";
                String ops      = "operations = " + opsCounter + ", ";
                logger.info(ops + timing);
            }
            opsCounter = 0;
        }
    }

    public boolean isEmptyQueue()
    {
        return databaseOperations.isEmpty();
    }

    public void runOps()
    {
        locked = true;
        Connection connection = null;
        try
        {
            connection = databaseConnection.getDataSource().getConnection();

            DatabaseOperation databaseOperation;
            while ((databaseOperation = databaseOperations.poll()) != null)
            {
                databaseOperation.writePaper(connection, databaseConnection);
                opsCounter++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            databaseConnection.close(connection);
        }
        locked = false;
    }

}

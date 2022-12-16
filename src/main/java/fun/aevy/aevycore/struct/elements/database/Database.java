package fun.aevy.aevycore.struct.elements.database;

import fun.aevy.aevycore.struct.manager.DatabasesManager;
import fun.aevy.aevycore.utils.builders.AverageBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.logging.Logger;

/**
 * Represents a database worker.
 * @since 1.4
 * @author Sorridi
 */
@SuppressWarnings({"BusyWait", "unused"})
@Getter
public class Database extends Thread
{
    private final JavaPlugin        javaPlugin;
    private final Logger            logger;
    private final DatabasesManager  databasesManager;

    @Setter
    private DatabaseConnection databaseConnection;
    private final ConcurrentLinkedQueue<DatabaseOperation> operations;

    private boolean debug, running;
    private long    timeToWait;
    private int     opsCounter;

    private final StampedLock lock;

    /**
     * Creates a new database worker.
     * @param javaPlugin            The plugin.
     * @param databasesManager      The databases manager.
     * @param databaseConnection    The database connection.
     */
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
        this.operations         = new ConcurrentLinkedQueue<>();
        this.databaseConnection = databaseConnection;
        this.running            = true;
        this.lock               = new StampedLock();

        databasesManager.addDatabase(this);

        setName("aevy-db #" + databasesManager.getDatabasesNum());
        start();
    }

    /**
     * Sets the sleep time of the thread.
     * @param timeToWait    The time to wait.
     * @param timeUnit      The time unit.
     * @return The database.
     */
    public Database with(int timeToWait, TimeUnit timeUnit)
    {
        this.timeToWait = timeUnit.toMillis(timeToWait);
        return this;
    }

    /**
     * Adds a database operation to the queue.
     * @param databaseOperation The database operation.
     */
    public void add(@Nullable DatabaseOperation databaseOperation)
    {
        if (databaseOperation == null)
            return;

        operations.add(databaseOperation);
    }

    /**
     * Sets the debug mode.
     * @param value The value.
     * @return The database.
     */
    public Database debug(boolean value)
    {
        debug = value;
        return this;
    }

    /**
     * Stops the thread.
     */
    public void stopRunning()
    {
        running = false;
    }

    @SneakyThrows
    @Override
    public void run()
    {
        val average = new AverageBuilder(1);

        while (running)
        {
            if (isEmptyQueue())
            {
                Thread.sleep(timeToWait);
                continue;
            }

            average.setStart();
            runOps();
            average.setEnd();

            if (debug)
            {
                String timing   = "time = " + average.get() + "ms";
                String ops      = "op(s) = " + opsCounter + ", ";
                logger.info(ops + timing);
            }
            opsCounter = 0;
        }
    }

    /**
     * Checks if the queue is empty.
     * @return {@code true} if the queue is empty.
     */
    public boolean isEmptyQueue()
    {
        return operations.isEmpty();
    }

    /**
     * Runs the operations.
     */
    public void runOps()
    {
        long stamp = lock.writeLock();
        Connection connection = null;
        try
        {
            connection = databaseConnection.getDataSource().getConnection();

            DatabaseOperation databaseOperation;
            while ((databaseOperation = operations.poll()) != null)
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
            lock.unlockWrite(stamp);
        }
    }

}

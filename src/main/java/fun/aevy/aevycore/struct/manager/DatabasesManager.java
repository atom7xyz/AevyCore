package fun.aevy.aevycore.struct.manager;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.struct.elements.database.Database;
import fun.aevy.aevycore.struct.elements.database.DatabaseConnection;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Manages all the databases.
 */
@SuppressWarnings("unused")
public class DatabasesManager
{
    private final Logger logger;

    private final HashMap<String, DatabaseConnection>   databaseConnections;
    private final Collection<Database>                  databases;

    /**
     * Creates a new databases manager.
     * @param aevyCore The AevyCore instance.
     */
    public DatabasesManager(AevyCore aevyCore)
    {
        logger = aevyCore.getLogger();

        databases           = new ArrayList<>();
        databaseConnections = new HashMap<>();
    }

    /**
     * Adds a new database connection.
     * @param databaseConnection The database connection.
     */
    public void addConnection(String name, DatabaseConnection databaseConnection)
    {
        databaseConnections.put(name, databaseConnection);
    }

    /**
     * Removes a database connection.
     * @param name The name of the database connection.
     */
    public void removeConnection(String name)
    {
        databaseConnections.remove(name);
    }

    /**
     * Gets a database connection from name.
     * @param name The name of the database connection.
     * @return The database connection.
     */
    public DatabaseConnection getConnection(String name)
    {
        return databaseConnections.get(name);
    }

    /**
     * Adds one or more databases.
     * @param database The database.
     */
    public void addDatabase(Database... database)
    {
        databases.addAll(Arrays.asList(database));
    }

    /**
     * Removes one or more databases.
     * @param database The database.
     */
    public void removeDatabase(Database database)
    {
        databases.removeIf(d -> d.equals(database));
    }

    /**
     * Gets the number of databases.
     * @return The number of databases.
     */
    public int getDatabasesNum()
    {
        return databases.size();
    }

    /**
     * Shuts down all the databases.
     */
    @SneakyThrows
    public void shutdown()
    {
        for (Database database : databases)
        {
            String name = "[" + database.getName() + "]";

            while (!database.isEmptyQueue())
            {
                Thread.sleep(500);
                logger.warning("WAITING FOR NON-EMPTY QUEUE " + name);
            }

            database.stopRunning();

            while (database.getLock().isWriteLocked())
            {
                Thread.sleep(500);
                logger.warning("WAITING FOR LOCKED DATABASE " + name);
            }
        }

        databaseConnections.clear();
        databases.clear();
    }

}

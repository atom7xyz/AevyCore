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

@SuppressWarnings("unused")
public class DatabasesManager
{
    private final Logger    logger;

    private final HashMap<String, DatabaseConnection>   databaseConnections;
    private final Collection<Database>                  databases;

    public DatabasesManager(AevyCore aevyCore)
    {
        logger = aevyCore.getLogger();

        databases           = new ArrayList<>();
        databaseConnections = new HashMap<>();
    }

    public void addConnection(String name, DatabaseConnection databaseConnection)
    {
        databaseConnections.put(name, databaseConnection);
    }

    public void removeConnection(String name)
    {
        databaseConnections.remove(name);
    }

    public DatabaseConnection getConnection(String name)
    {
        return databaseConnections.get(name);
    }

    public void addDatabase(Database... database)
    {
        databases.addAll(Arrays.asList(database));
    }

    public void removeDatabase(Database database)
    {
        databases.removeIf(d -> d.equals(database));
    }

    public int getDatabasesNum()
    {
        return databases.size();
    }

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

            while (database.isLocked())
            {
                Thread.sleep(500);
                logger.warning("WAITING FOR LOCKED DATABASE " + name);
            }
        }

        databaseConnections.clear();
        databases.clear();
    }

}

package fun.aevy.aevycore.struct.elements.database;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

/**
 * Interface for Database operations.
 * @since 1.4
 * @author Sorridi
 */
public interface DatabaseOperation
{
    void writePaper(@NotNull Connection connection, @NotNull DatabaseConnection databaseConnection);
}

package fun.aevy.aevycore.struct.elements.database;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a query to be executed on a database.
 * @since 1.0
 * @author Sorridi
 */
@SuppressWarnings("unused")
@Getter
public class Query
{
    private final String table;

    public Query(@NotNull String table)
    {
        this.table = table;
    }

    /**
     * Creates a query to select all the rows from a table.
     * @return The query (SELECT * FROM table;).
     */
    public String selectAll()
    {
        return "SELECT * FROM " + table + ";";
    }

    /**
     * Creates a query to select a specific column from a table.
     * @param field The column to select.
     * @return The query (SELECT column FROM table;).
     */
    public String select(@NotNull String field)
    {
        return "SELECT " + field + " FROM " + table + ";";
    }

    /**
     * Creates a query to truncate a table.
     * @return The query (TRUNCATE TABLE table;).
     */
    public String truncate()
    {
        return "TRUNCATE TABLE " + table + ";";
    }

    /**
     * Creates a query to select a specific column from a table.
     * @param target    The column to select.
     * @param field     The field to select from.
     * @param value     The value to select.
     * @return The query (SELECT target FROM table WHERE field = value;).
     */
    public String selectWhere(
            @NotNull String target,
            @NotNull String field,
            @NotNull Object value
    ) {
        return "SELECT " + target + " FROM " + table + " WHERE " + field + " = " + convertObj(value) + ";";
    }

    /**
     * Creates a query to delete a row from a table.
     * @param field The field to delete from.
     * @param value The value to check.
     * @return The query (DELETE FROM table WHERE field = value;).
     */
    public String deleteWhere(@NotNull String field, @NotNull Object value)
    {
        return "DELETE FROM " + table + " WHERE " + field + " = " + convertObj(value) + ";";
    }

    /**
     * Creates a query to select a specific column from a table.
     * @param target    The column to select.
     * @param field     The field to select from.
     * @return The query (SELECT target FROM table WHERE field = ?;).
     */
    public String pendingSelectWhere(@NotNull String target, @NotNull String field)
    {
        return "SELECT " + target + " FROM " + table + " WHERE " + field + " = ?;";
    }

    /**
     * Creates a query to update a row in a table.
     * @param target    The column to update.
     * @param field     The field to update.
     * @return The query (UPDATE table SET field = ? WHERE target = ?;).
     */
    public String pendingUpdateWhere(@NotNull String target, @NotNull String field)
    {
        return "UPDATE " + table + " SET " + field + " = ? WHERE " + target + " = ?;";
    }

    /**
     * Creates a query to delete a row from a table.
     * @param field The field to delete from.
     * @return The query (DELETE FROM table WHERE field = ?;).
     */
    public String pendingDeleteWhere(@NotNull String field)
    {
        return "DELETE FROM " + table + " WHERE " + field + " = ?;";
    }

    /**
     * Creates a query to select all the rows from a table.
     * @param field The field to select from.
     * @param value The value to check.
     * @return The query (SELECT * FROM table WHERE field = value;).
     */
    public String selectAllWhere(@NotNull String field, @NotNull Object value)
    {
        return "SELECT * FROM " + table + " WHERE " + field + " = " + convertObj(value) + ";";
    }

    /**
     * Creates a query to select all the rows from a table.
     * @param field The field to select from.
     * @return The query (SELECT * FROM table WHERE field = ?;).
     */
    public String pendingSelectAllWhere(@NotNull String field)
    {
        return "SELECT * FROM " + table + " WHERE " + field + " = ?;";
    }

    /**
     * Creates a query to insert a row in a table.
     * @param fields The fields to insert.
     * @param values The values to insert.
     * @return The query (INSERT INTO table (fields) VALUES (values);).
     */
    public String insert(@NotNull String[] fields, @NotNull Object[] values)
    {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");

        stringBuilder
                .append(table)
                .append(" (");

        for (int i = 0; i < fields.length; i++) {
            stringBuilder.append(fields[i]);

            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder
                .append(") ")
                .append("VALUES (");

        for (int i = 0; i < values.length; i++)
        {
            stringBuilder.append(convertObj(values[i]));

            if (i != values.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder.append(");");

        return stringBuilder.toString();
    }

    /**
     * Creates a query to insert a row in a table.
     * @param fields The fields to insert.
     * @return The query (INSERT INTO table (fields) VALUES (?);).
     */
    public String pendingInsert(@NotNull String[] fields)
    {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");

        stringBuilder
                .append(table)
                .append(" (");

        for (int i = 0; i < fields.length; i++)
        {
            stringBuilder.append(fields[i]);

            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder
                .append(") ")
                .append("VALUES (");

        for (int i = 0; i < fields.length; i++)
        {
            stringBuilder.append("?");

            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder.append(");");

        return stringBuilder.toString();
    }

    /**
     * Creates a query to update a row in a table.
     * @param fields        The fields to update.
     * @param values        The values to update.
     * @param target        The column to update.
     * @param targetValue   The value to check.
     * @return The query (UPDATE table SET fields = values WHERE target = targetValue;).
     */
    public String update(
            @NotNull String[] fields,
            @NotNull Object[] values,
            @NotNull String target,
            @NotNull Object targetValue
    ) {
        StringBuilder stringBuilder = new StringBuilder("UPDATE ");

        stringBuilder
                .append(table)
                .append(" SET ");

        for (int i = 0; i < fields.length; i++)
        {
            stringBuilder
                    .append(fields[i])
                    .append(" = ")
                    .append(convertObj(values[i]));

            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder
                .append(" WHERE ")
                .append(target)
                .append(" = ")
                .append(convertObj(targetValue))
                .append(";");

        return stringBuilder.toString();
    }

    /**
     * Creates a query to update a row in a table.
     * @param fields The fields to update.
     * @param target The column to update.
     * @return The query (UPDATE table SET fields = ? WHERE target = ?;).
     */
    public String pendingUpdateWhere(@NotNull String[] fields, @NotNull String target)
    {
        StringBuilder stringBuilder = new StringBuilder("UPDATE ");

        stringBuilder
                .append(table)
                .append(" SET ");

        for (int i = 0; i < fields.length; i++)
        {
            stringBuilder
                    .append(fields[i])
                    .append(" = ?");

            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder
                .append(" WHERE ")
                .append(target)
                .append(" = ?;");

        return stringBuilder.toString();
    }

    /**
     * Creates a query to create a table.
     * @param fields The fields to create.
     * @return The query (CREATE TABLE IF NOT EXISTS table (fields);).
     */
    public String createTable(String[] fields)
    {
        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS ");

        stringBuilder
                .append(table)
                .append(" (");

        for (int i = 0; i < fields.length; i++)
        {
            stringBuilder.append(fields[i]);
            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder.append(");");

        return stringBuilder.toString();
    }

    /**
     * Creates a query to insert an entry in a table if it doesn't exist.
     * @param fields The fields to insert.
     * @param values The values to insert.
     * @param target The column to check.
     * @param object The value to check.
     * @return The query (INSERT INTO table (fields) SELECT * FROM (SELECT values AS field) AS tmp WHERE NOT EXISTS (SELECT value AS field FROM table WHERE target = object) LIMIT 1;).
     */
    public String insertIfNotExist(
            @NotNull String[] fields,
            @NotNull Object[] values,
            @NotNull String target,
            @NotNull Object object
    ) {

        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");

        stringBuilder
                .append(table)
                .append(" (");

        for (int i = 0; i < fields.length; i++)
        {
            stringBuilder.append(fields[i]);
            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder.append(") SELECT * FROM (SELECT ");

        for (int i = 0; i < values.length; i++)
        {
            stringBuilder
                    .append(convertObj(values[i]))
                    .append(" AS ")
                    .append(fields[i]);

            if (i != values.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder
                .append(") AS tmp WHERE NOT EXISTS (SELECT ")
                .append(target)
                .append(" FROM ")
                .append(table)
                .append(" WHERE ")
                .append(target)
                .append(" = ")
                .append(convertObj(object))
                .append(") LIMIT 1;");

        return stringBuilder.toString();
    }

    /**
     * Converts an object to a SQL compatible value.
     * @param object The object to convert.
     * @return The SQL compatible value.
     */
    private String convertObj(@NotNull Object object)
    {
        StringBuilder stringBuilder = new StringBuilder();

        if (object instanceof String)
        {
            stringBuilder
                    .append("\"")
                    .append(object)
                    .append("\"");
        }
        else if (object instanceof Boolean)
        {
            stringBuilder.append((boolean) object ? 1 : 0);
        }
        else
        {
            stringBuilder.append(object);
        }

        return stringBuilder.toString();
    }
}

package fun.aevy.aevycore.struct.elements.database;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@Getter
public class Query
{
    private final String table;

    public Query(@NotNull String table)
    {
        this.table = table;
    }

    public String selectAll()
    {
        return "SELECT * FROM " + table + ";";
    }

    public String truncate()
    {
        return "TRUNCATE TABLE " + table + ";";
    }

    public String select(@NotNull String field)
    {
        return "SELECT " + field + " FROM " + table + ";";
    }

    public String selectWhere(
            @NotNull String target,
            @NotNull String field,
            @NotNull Object value
    ) {
        return "SELECT " + target + " FROM " + table + " WHERE " + field + " = " + convertObj(value) + ";";
    }

    public String deleteWhere(
            @NotNull String target,
            @NotNull String field,
            @NotNull Object value
    ) {
        return "DELETE FROM " + table + " WHERE " + field + " = " + convertObj(value) + ";";
    }

    public String pendingSelectWhere(@NotNull String target, @NotNull String field)
    {
        return "SELECT " + target + " FROM " + table + " WHERE " + field + " = ?;";
    }

    public String pendingUpdateWhere(@NotNull String target, @NotNull String field)
    {
        return "UPDATE " + table + " SET " + field + " = ? WHERE " + target + " = ?;";
    }

    public String pendingDeleteWhere(@NotNull String target, @NotNull String field)
    {
        return "DELETE FROM " + table + " WHERE " + field + " = ?;";
    }

    public String selectAllWhere(@NotNull String field, @NotNull Object value)
    {
        return "SELECT * FROM " + table + " WHERE " + field + " = " + convertObj(value) + ";";
    }

    public String pendingSelectAllWhere(@NotNull String field)
    {
        return "SELECT * FROM " + table + " WHERE " + field + " = ?;";
    }

    public String insert(@NotNull String[] fields, @NotNull Object[] values)
    {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
        stringBuilder.append(table);

        stringBuilder.append(" (");
        for (int i = 0; i < fields.length; i++) {
            stringBuilder.append(fields[i]);

            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }
        stringBuilder.append(") ").append("VALUES (");

        for (int i = 0; i < values.length; i++)
        {
            stringBuilder.append(convertObj(values[i]));

            if (i != values.length - 1)
                stringBuilder.append(", ");
        }
        stringBuilder.append(");");

        return stringBuilder.toString();
    }

    public String pendingInsert(@NotNull String[] fields)
    {
        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");
        stringBuilder.append(table);

        stringBuilder.append(" (");
        for (int i = 0; i < fields.length; i++)
        {
            stringBuilder.append(fields[i]);

            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }
        stringBuilder.append(") ").append("VALUES (");

        for (int i = 0; i < fields.length; i++)
        {
            stringBuilder.append("?");

            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

    public String update(
            @NotNull String[] fields,
            @NotNull Object[] values,
            @NotNull String target,
            @NotNull Object targetValue
    ) {
        StringBuilder stringBuilder = new StringBuilder("UPDATE ");

        stringBuilder.append(table).append(" SET ");

        for (int i = 0; i < fields.length; i++)
        {
            stringBuilder.append(fields[i]).append(" = ").append(convertObj(values[i]));

            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder.append(" WHERE ").append(target).append(" = ").append(convertObj(targetValue)).append(";");

        return stringBuilder.toString();
    }

    public String pendingUpdateWhere(@NotNull String[] fields, @NotNull String target)
    {
        StringBuilder stringBuilder = new StringBuilder("UPDATE ");

        stringBuilder.append(table).append(" SET ");

        for (int i = 0; i < fields.length; i++)
        {
            stringBuilder.append(fields[i]).append(" = ?");

            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder.append(" WHERE ").append(target).append(" = ?;");

        return stringBuilder.toString();
    }

    public String createTable(String[] fields)
    {
        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS ");

        stringBuilder.append(table).append(" (");

        for (int i = 0; i < fields.length; i++)
        {
            stringBuilder.append(fields[i]);
            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder.append(");");

        return stringBuilder.toString();
    }

    public String insertIfNotExist(
            @NotNull String[] fields,
            @NotNull Object[] values,
            @NotNull String target,
            @NotNull Object object
    ) {

        StringBuilder stringBuilder = new StringBuilder("INSERT INTO ");

        stringBuilder.append(table).append(" (");

        for (int i = 0; i < fields.length; i++)
        {
            stringBuilder.append(fields[i]);
            if (i != fields.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder.append(") SELECT * FROM (SELECT ");

        for (int i = 0; i < values.length; i++)
        {
            stringBuilder.append(convertObj(values[i])).append(" AS ").append(fields[i]);

            if (i != values.length - 1)
                stringBuilder.append(", ");
        }

        stringBuilder.append(") AS tmp WHERE NOT EXISTS (SELECT ").append(target).append(" FROM ")
                .append(table).append(" WHERE ").append(target).append(" = ");

        stringBuilder.append(convertObj(object)).append(") LIMIT 1;");

        return stringBuilder.toString();
    }

    private String convertObj(@NotNull Object object)
    {
        StringBuilder stringBuilder = new StringBuilder();

        if (object instanceof String)
            stringBuilder.append("\"").append(object).append("\"");
        else if (object instanceof Boolean)
            stringBuilder.append((Boolean) object ? 1 : 0);
        else
            stringBuilder.append(object);

        return stringBuilder.toString();
    }
}

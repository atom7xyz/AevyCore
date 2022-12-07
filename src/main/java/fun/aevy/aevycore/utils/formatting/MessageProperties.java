package fun.aevy.aevycore.utils.formatting;

import fun.aevy.aevycore.utils.Shortcuts;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
import lombok.Getter;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MessageProperties
{
    private String prefix;
    private boolean prefixed;

    private String          primitiveMessage, actualMessage;
    private List<String>    primitiveList, actualList;

    private Type type;

    public MessageProperties(@NotNull String string)
    {
        initWith(string);
    }

    public MessageProperties(@NotNull List<String> primitiveList)
    {
        initWith(primitiveList);
    }

    public MessageProperties(CoolConfig coolConfig, Enum<?> e)
    {
        initWith(coolConfig.getValue(e));
    }

    public MessageProperties(String primitiveMessage, String prefix)
    {
        initWith(primitiveMessage, prefix);
    }

    public MessageProperties(List<String> value, String prefix)
    {
        initWith(value, prefix);
    }

    private void initWith(Object object)
    {
        if (object instanceof String)
        {
            this.primitiveMessage   = (String) object;
            this.actualMessage      = null;
            this.type               = Type.STRING;
        }
        else if (object instanceof List)
        {
            this.primitiveList  = (List<String>) object;
            this.actualList     = null;
            this.type           = Type.LIST;
        }
    }

    private void initWith(Object object, String prefix)
    {
        this.prefix     = prefix;
        this.prefixed   = true;

        initWith(object);
    }

    public MessageProperties replace(String toReplace, Object replacement)
    {
        String rep = String.valueOf(replacement);

        switch (type)
        {
            case STRING:
            {
                actualMessage = Shortcuts.color(primitiveMessage.replace(toReplace, rep));
                break;
            }
            case LIST:
            {
                actualList = new ArrayList<>(primitiveList);
                actualList.replaceAll(s -> Shortcuts.color(s.replace(toReplace, rep)));
                break;
            }
        }

        return this;
    }

    public MessageProperties replace(int index, String toReplace, String replacement)
    {
        switch (type)
        {
            case STRING:
            {
                actualMessage = Shortcuts.color(primitiveMessage.replace(toReplace, replacement));
                break;
            }
            case LIST:
            {
                actualList.set(index, Shortcuts.color(primitiveList.get(index).replace(toReplace, replacement)));
                break;
            }
        }

        return this;
    }

    public MessageProperties replace(String[] toReplace, Object... replacements)
    {
        int length = toReplace.length;

        switch (type)
        {
            case STRING:
            {
                String temp = primitiveMessage;

                for (int i = 0; i < length; i++)
                {
                    temp = temp.replace(toReplace[i], replacements[i].toString());
                }
                actualMessage = Shortcuts.color(temp);
                break;
            }
            case LIST:
            {
                val temp = new ArrayList<>(primitiveList);

                for (int i = 0; i < length; i++)
                {
                    int a = i;
                    temp.replaceAll(s -> Shortcuts.color(s.replace(toReplace[a], replacements[a].toString())));
                }
                actualList = temp;
                break;
            }
        }

        return this;
    }

    public MessageProperties replace(int index, String[] toReplace, Object... replacements)
    {
        int length = toReplace.length;

        for (int i = 0; i < length; i++)
        {
            replace(index, toReplace[i], replacements[i].toString());
        }

        return this;
    }

    private void updatePrefix(String prefix)
    {
        switch (type)
        {
            case STRING:
            {
                primitiveMessage = Shortcuts.color(primitiveMessage.replace("{prefix}", prefix));
                actualMessage = primitiveMessage;
                break;
            }
            case LIST:
            {
                primitiveList.replaceAll(s -> Shortcuts.color(s.replace("{prefix}", prefix)));
                actualList = primitiveList;
                break;
            }
        }
    }

    public MessageProperties withPrefix(String prefix)
    {
        this.prefix = prefix;
        prefixed    = true;

        updatePrefix(prefix);

        return this;
    }

    public MessageProperties noPrefix()
    {
        this.prefix = "";
        prefixed    = false;

        updatePrefix(prefix);

        return this;
    }

    public String getMessage()
    {
        return actualMessage == null ? "" : actualMessage;
    }

    public List<String> getMessages()
    {
        return actualList;
    }

    public enum Type
    {
        STRING,
        LIST
    }

}
package fun.aevy.aevycore.utils.formatting;

import fun.aevy.aevycore.utils.Shortcuts;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class MessageProperties
{
    private String  primitiveMessage, prefix, actualMessage;
    private boolean prefixed;

    private List<String> primitiveList, actualList;

    public MessageProperties(String primitiveMessage)
    {
        if (primitiveMessage == null)
        {
            this.primitiveMessage   = "";
            this.actualMessage      = "";
        }
        else
        {
            this.primitiveMessage   = primitiveMessage;
            this.actualMessage      = primitiveMessage;
        }
    }

    public MessageProperties(List<String> primitiveList)
    {
        if (primitiveList == null)
        {
            this.primitiveList   = Collections.emptyList();
            this.actualList      = Collections.emptyList();
        }
        else
        {
            this.primitiveList = primitiveList;
            this.actualList    = primitiveList;
        }
    }

    public MessageProperties(CoolConfig coolConfig, Enum<?> e)
    {
        Object object = coolConfig.getValue(e);

        if (object instanceof String)
        {
            primitiveMessage    = (String) object;
            actualMessage       = primitiveMessage;
        }
        else
        {
            primitiveList   = (List<String>) object;
            actualList      = primitiveList;
        }
    }

    public MessageProperties(String primitiveMessage, String prefix)
    {
        this.primitiveMessage   = primitiveMessage;
        this.actualMessage      = primitiveMessage;
        this.prefix             = prefix;
        this.prefixed           = true;
    }

    public MessageProperties(List<String> value, String prefix)
    {
        this.primitiveList = value;
        this.actualList    = value;
        this.prefix        = prefix;
        this.prefixed      = true;
    }

    public MessageProperties replace(String toReplace, Object replacement)
    {
        if (primitivesAreNull())
        {
            return this;
        }

        String strReplacement = String.valueOf(replacement);

        if (primitiveMessage == null)
        {
            List<String> temp = new ArrayList<>(primitiveList);
            temp.replaceAll(s -> Shortcuts.color(s.replace(toReplace, strReplacement)));
            actualList = temp;
        }
        else
        {
            actualMessage = Shortcuts.color(primitiveMessage.replace(toReplace, strReplacement));
        }
        return this;
    }

    public MessageProperties replace(int index, String toReplace, String replacement)
    {
        if (primitivesAreNull())
        {
            return this;
        }

        String strReplacement = String.valueOf(replacement);

        if (primitiveMessage == null)
        {
            actualList.set(index, Shortcuts.color(primitiveList.get(index).replace(toReplace, strReplacement)));
        }
        else
        {
            actualMessage = Shortcuts.color(primitiveMessage.replace(toReplace, strReplacement));
        }
        return this;
    }

    public MessageProperties replace(String[] toReplace, Object... replacements)
    {
        if (primitivesAreNull())
        {
            return this;
        }

        int length              = toReplace.length;
        String[] strReplacement = new String[length];

        for (int i = 0; i < length; i++)
        {
            strReplacement[i] = String.valueOf(replacements[i]);
        }

        if (primitiveMessage == null)
        {
            List<String> temp = new ArrayList<>(primitiveList);

            for (int i = 0; i < length; i++)
            {
                int a = i;
                temp.replaceAll(s -> Shortcuts.color(s.replace(toReplace[a], strReplacement[a])));
            }
            actualList = temp;
        }
        else
        {
            String temp = primitiveMessage;

            for (int i = 0; i < length; i++)
            {
                temp = temp.replace(toReplace[i], strReplacement[i]);
            }
            actualMessage = Shortcuts.color(temp);
        }
        return this;
    }

    public MessageProperties replace(int index, String[] toReplace, Object... replacements)
    {
        if (primitivesAreNull())
        {
            return this;
        }

        int length              = toReplace.length;
        String[] strReplacement = new String[length];

        for (int i = 0; i < length; i++)
        {
            strReplacement[i] = String.valueOf(replacements[i]);
        }

        if (primitiveMessage == null)
        {
            for (int i = 0; i < length; i++)
            {
                replace(index, toReplace[i], strReplacement[i]);
            }
        }
        else
        {
            for (int i = 0; i < length; i++)
            {
                replace(index, toReplace[i], strReplacement[i]);
            }
        }
        return this;
    }

    public MessageProperties withPrefix(String prefix)
    {
        this.prefix = prefix;
        prefixed    = true;

        if (primitiveMessage == null)
        {
            primitiveList.replaceAll(s -> Shortcuts.color(s.replace("{prefix}", prefix)));
            actualList = primitiveList;
        }
        else
        {
            primitiveMessage = Shortcuts.color(primitiveMessage.replace("{prefix}", prefix));
            actualMessage = primitiveMessage;
        }

        return this;
    }

    public MessageProperties noPrefix()
    {
        this.prefix = "";
        prefixed    = false;

        if (primitiveMessage == null)
        {
            primitiveList.replaceAll(s -> Shortcuts.color(s.replace("{prefix}", prefix)));
            actualList = primitiveList;
        }
        else
        {
            primitiveMessage = Shortcuts.color(primitiveMessage.replace("{prefix}", prefix));
            actualMessage = primitiveMessage;
        }

        return this;
    }

    public String getMessage()
    {
        return actualMessage;
    }

    public List<String> getMessages()
    {
        return actualList;
    }

    private boolean primitivesAreNull()
    {
        return primitiveMessage == null && primitiveList == null;
    }

}
package fun.aevy.aevycore.utils.formatting;

import fun.aevy.aevycore.utils.Shortcuts;
import lombok.Getter;

public class MessageProperties
{
    @Getter
    private final String primitiveMessage;
    @Getter
    private String prefix, actualMessage;
    @Getter
    private boolean prefixed;

    public MessageProperties(String primitiveMessage)
    {
        this.primitiveMessage   = primitiveMessage;
        this.actualMessage      = primitiveMessage;
    }

    public MessageProperties(String primitiveMessage, String prefix)
    {
        this.primitiveMessage   = primitiveMessage;
        this.actualMessage      = primitiveMessage;
        this.prefix             = prefix;
        this.prefixed           = true;
    }

    public MessageProperties replace(String[] toReplace, String[] replacements)
    {
        String temp = primitiveMessage;

        for (int i = 0; i < toReplace.length; i++)
        {
            temp = temp.replace(toReplace[i], replacements[i]);
        }
        actualMessage = temp;

        return this;
    }

    public MessageProperties withPrefix(String prefix)
    {
        this.prefix = Shortcuts.color(prefix);
        prefixed    = true;

        return this;
    }

    public MessageProperties noPrefix()
    {
        prefixed = false;
        return this;
    }

    public String getMessage()
    {
        if (prefixed)
        {
            return Shortcuts.color(prefix + actualMessage);
        }
        return Shortcuts.color(actualMessage);
    }

}

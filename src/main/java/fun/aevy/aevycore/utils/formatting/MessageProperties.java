package fun.aevy.aevycore.utils.formatting;

import fun.aevy.aevycore.utils.Shortcuts;
import lombok.Getter;

@Getter
public class MessageProperties
{
    private final String    primitiveMessage;
    private String          actualMessage;
    private String          prefix;

    public MessageProperties(String primitiveMessage)
    {
        this.primitiveMessage   = primitiveMessage;
        this.actualMessage      = primitiveMessage;
    }

    public MessageProperties(String primitiveMessage, String prefix)
    {
        this.primitiveMessage   = primitiveMessage;
        this.actualMessage      = Shortcuts.color(primitiveMessage);
        this.prefix             = Shortcuts.color(prefix);
    }

    public MessageProperties replace(String[] toReplace, String[] replacements)
    {
        String temp = primitiveMessage;

        for (int i = 0; i < toReplace.length; i++)
        {
            temp = temp.replace(toReplace[i], replacements[i]);
        }
        actualMessage = Shortcuts.color(temp);

        return this;
    }

    public MessageProperties withPrefix(String prefix)
    {
        this.prefix = Shortcuts.color(prefix);
        return this;
    }

    public MessageProperties noPrefix()
    {
        this.prefix = null;
        return this;
    }

}

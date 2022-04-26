package fun.aevy.aevycore.utils.formatting;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.utils.strings.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Send
{
    private final StringUtils stringUtils;

    /**
     * Constructor for Send.
     * @param aevyCore Instance of AevyCore.
     * @since 1.0
     */
    public Send(AevyCore aevyCore)
    {
        stringUtils = aevyCore.getStringUtils();
    }

    /**
     * Sends the message to the commandSender.
     * @param commandSender Target of the message.
     * @param message       The message to be sent.
     * @param messageResult Typology of the message.
     * @since 1.0
     */
    public void message(CommandSender commandSender, String message, MessageResult messageResult)
    {
        commandSender.sendMessage(stringUtils.formatMessage(message, messageResult));
    }

    /**
     * Sends a list of messages to the commandSender.
     * @param commandSender Target of the messages.
     * @param messages      The messages to be sent.
     * @param messageResult Typology of the messages.
     * @since 1.0
     */
    public void message(CommandSender commandSender, List<String> messages, MessageResult messageResult)
    {
        messages.forEach(s -> message(commandSender, s, messageResult));
    }

    /**
     * Sends the message to the commandSender with {@link fun.aevy.aevycore.utils.formatting.MessageResult#NORMAL}.
     * @param commandSender Target of the message.
     * @param message       The message to be sent.
     * @since 1.0
     */
    public void message(CommandSender commandSender, String message)
    {
        message(commandSender, message, MessageResult.NORMAL);
    }

    /**
     * Sends the messages to the commandSender with {@link fun.aevy.aevycore.utils.formatting.MessageResult#NORMAL}.
     * @param commandSender Target of the messages.
     * @param messages      The messages to be sent.
     * @since 1.0
     */
    public void message(CommandSender commandSender, List<String> messages)
    {
        messages.forEach(s -> message(commandSender, s));
    }

    /**
     * Sends the message to the commandSender with {@link fun.aevy.aevycore.utils.formatting.MessageResult#ERROR}.
     * @param commandSender Target of the message.
     * @param message       The message to be sent.
     * @since 1.0
     */
    public void errorMessage(CommandSender commandSender, String message)
    {
        message(commandSender, message, MessageResult.ERROR);
    }

    /**
     * Sends the messages to the commandSender with {@link fun.aevy.aevycore.utils.formatting.MessageResult#ERROR}.
     * @param commandSender Target of the messages.
     * @param messages      The messages to be sent.
     * @since 1.0
     */
    public void errorMessage(CommandSender commandSender, List<String> messages)
    {
        messages.forEach(s -> errorMessage(commandSender, s));
    }

    /**
     * Sends the message to the commandSender with {@link fun.aevy.aevycore.utils.formatting.MessageResult#SUCCESS}.
     * @param commandSender Target of the message.
     * @param message       The message to be sent.
     * @since 1.0
     */
    public void successMessage(CommandSender commandSender, String message)
    {
        message(commandSender, message, MessageResult.SUCCESS);
    }

    /**
     * Sends the messages to the commandSender with {@link fun.aevy.aevycore.utils.formatting.MessageResult#SUCCESS}.
     * @param commandSender Target of the messages.
     * @param messages      The messages to be sent.
     * @since 1.0
     */
    public void successMessage(CommandSender commandSender, List<String> messages)
    {
        messages.forEach(s -> successMessage(commandSender, s));
    }

}
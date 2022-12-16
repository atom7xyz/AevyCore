package fun.aevy.aevycore.utils.builders;

import fun.aevy.aevycore.AevyCore;
import fun.aevy.aevycore.struct.elements.AevyDependent;
import fun.aevy.aevycore.struct.elements.Reloadable;
import fun.aevy.aevycore.utils.configuration.elements.CoolConfig;
import fun.aevy.aevycore.utils.configuration.entries.Aevy;
import fun.aevy.aevycore.utils.formatting.MessageProperties;
import fun.aevy.aevycore.utils.formatting.Send;
import lombok.Getter;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Creates and registers new {@link Command}s.
 * @since 1.0
 * @author Sorridi, Niketion
 */
@SuppressWarnings("unused")
@Getter
public abstract class CommandsBuilder implements CommandExecutor, TabCompleter, Reloadable
{
    private boolean             onlyPlayer, onlyConsole, tabComplete;
    private String              permission;
    private final String        command;
    private MessageProperties   usage;

    protected final AevyDependent aevyDependent;

    private final JavaPlugin    plugin;
    protected final AevyCore    aevyCore;
    protected final CoolConfig  coolConfig;
    protected final Send        send;
    protected MessageProperties noPlayer, noConsole, noPerms, unknownPlayer;

    /**
     * Constructor for new Commands, which they get automatically registered.
     *
     * @param aevyDependent The AevyDependent instance.
     * @param command       The command itself.
     */
    public CommandsBuilder(
            @NotNull    AevyDependent   aevyDependent,
            @NotNull    String          command
    ) {
        this.aevyDependent  = aevyDependent;
        this.aevyCore       = aevyDependent.getAevyCore();
        this.coolConfig     = aevyDependent.getCoolConfig();
        this.send           = aevyDependent.getSend();
        this.plugin         = aevyDependent.getCurrentPlugin();

        this.command        = command;
        this.onlyPlayer     = false;
        this.onlyConsole    = false;
        this.usage          = null;
        this.tabComplete    = false;
        this.permission     = null;
    }

    public abstract boolean command(CommandSender sender, String[] args);

    public abstract List<String> tabComplete(String[] args);

    @Override
    public boolean onCommand(
            @NotNull    CommandSender sender,
            @NotNull    Command command,
            @NotNull    String label,
                        String[] args
    ) {
        if (onlyConsole && sender instanceof Player)
        {
            Send.message(sender, noPlayer);
            return false;
        }

        if (onlyPlayer && sender instanceof ConsoleCommandSender)
        {
            Send.message(sender, noConsole);
            return false;
        }

        if (permission != null && !sender.hasPermission(permission))
        {
            Send.message(sender, noPerms);
            return false;
        }

        if (!command(sender, args))
        {
            if (usage == null)
                return false;

            Send.message(sender, usage);
            return false;
        }

        return true;
    }

    /**
     * Returns all the players names whose name starts with the argument.
     * @param args Command arguments.
     * @return List of players names.
     */
    public List<String> getDefaultTabList(String[] args)
    {
        List<String> names = new ArrayList<>();

        Collection<? extends Player> players = aevyCore.getServer().getOnlinePlayers();

        int length = args.length;
        String argString = args[--length];

        if (argString.isEmpty())
        {
            players.forEach(player -> names.add(player.getName()));
        }
        else
        {
            players
                    .stream()
                    .filter(player -> player.getName().startsWith(argString))
                    .forEach(player -> names.add(player.getName()));
        }

        return names;
    }

    /**
     * Returns all the command arguments available that starts with the argument.
     * @param args Command arguments.
     * @return List of arguments.
     */
    public List<String> getArgsTabList(String[] args)
    {
        List<String> list = new ArrayList<>();

        int length = args.length;
        String argString = args[--length];

        if (argString.isEmpty())
        {
            return Arrays.asList(args);
        }

        Arrays.stream(args)
                .filter(s -> s.startsWith(argString))
                .forEach(s -> list.add(argString));

        return list;
    }

    @Override
    public List<String> onTabComplete(
            @NotNull    CommandSender sender,
            @NotNull    Command command,
            @NotNull    String alias,
                        String[] args
    ) {
        int length = args.length;

        if (length >= 1)
        {
            List<String> tabCompleted = tabComplete(args);

            if (tabCompleted == null)
            {
                return Collections.emptyList();
            }

            return tabCompleted
                    .stream()
                    .filter(arg -> arg.startsWith(args[length - 1]))
                    .collect(Collectors.toList());
        }

        return getDefaultTabList(args);
    }

    /**
     * Sets if the command can only be executed by a player.
     * @param value The value to set.
     * @return The instance of the command.
     */
    public CommandsBuilder setOnlyPlayers(boolean value)
    {
        onlyPlayer = value;
        return this;
    }

    /**
     * Sets if the command can only be executed by the console.
     * @param value The value to set.
     * @return The instance of the command.
     */
    public CommandsBuilder setOnlyConsole(boolean value)
    {
        onlyConsole = value;
        return this;
    }

    /**
     * Sets if the command has a usage message.
     * @param value The value to set.
     * @return The instance of the command.
     */
    public CommandsBuilder setTabComplete(boolean value)
    {
        tabComplete = value;
        return this;
    }

    /**
     * Sets the usage of the command.
     * @param e The enum of the configuration message.
     * @return The instance of the command.
     */
    public CommandsBuilder setUsage(Enum<?> e)
    {
        this.usage = coolConfig.getProperties(e);
        return this;
    }

    /**
     * Sets the permission needed to execute the command.
     * @param message The permission needed.
     * @return The instance of the command.
     */
    public CommandsBuilder setPermission(String message)
    {
        this.permission = message;
        return this;
    }

    /**
     * Sets the permission needed to execute the command.
     * @param e The enum of the configuration message.
     * @return The instance of the command.
     */
    public CommandsBuilder setPermission(Enum<?> e)
    {
        this.permission = (String) coolConfig.getValue(e);
        return this;
    }

    /**
     * Builds the command.
     */
    public void build()
    {
        plugin.getCommand(command).setExecutor(this);

        if (tabComplete)
        {
            plugin.getCommand(command).setTabCompleter(this);
        }

        reloadDefaults();
        aevyDependent.addReloadable(this);
    }

    /**
     * Reloads the default messages.
     */
    public void reloadDefaults()
    {
        noPlayer        = coolConfig.getProperties(Aevy.Messages.NO_PLAYER);
        noConsole       = coolConfig.getProperties(Aevy.Messages.NO_CONSOLE);
        noPerms         = coolConfig.getProperties(Aevy.Messages.NO_PERMS);
        unknownPlayer   = coolConfig.getProperties(Aevy.Messages.UNKNOWN_PLAYER);
    }

}
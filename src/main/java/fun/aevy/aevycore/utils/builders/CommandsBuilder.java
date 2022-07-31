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
        this.aevyDependent = aevyDependent;

        this.aevyCore   = aevyDependent.getAevyCore();
        this.coolConfig = aevyDependent.getCoolConfig();
        this.send       = aevyDependent.getSend();
        this.plugin     = aevyDependent.getCurrentPlugin();

        this.command        = command;
        this.onlyPlayer     = false;
        this.onlyConsole    = false;
        this.usage          = null;
        this.tabComplete    = false;
        this.permission     = null;

        reloadVars();
    }

    /**
     * Constructor for new Commands, which they get automatically registered.
     *
     * @param aevyCore  The AevyDependent instance.
     * @param command   The command itself.
     */
    public CommandsBuilder(
            @NotNull    AevyCore    aevyCore,
            @NotNull    String      command
    ) {
        this.aevyDependent = null;

        this.aevyCore   = aevyCore;
        this.coolConfig = aevyCore.getCoolConfig();
        this.send       = aevyCore.getSend();
        this.plugin     = aevyCore;

        this.command        = command;
        this.onlyPlayer     = false;
        this.onlyConsole    = false;
        this.usage          = null;
        this.tabComplete    = false;
        this.permission     = null;

        reloadVars();
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
            send.message(sender, Aevy.Messages.NO_PLAYER);
            return false;
        }

        if (onlyPlayer && sender instanceof ConsoleCommandSender)
        {
            send.message(sender, Aevy.Messages.NO_CONSOLE);
            return false;
        }

        if (permission != null && !sender.hasPermission(permission))
        {
            send.message(sender, Aevy.Messages.NO_PERMS);
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
            players.forEach(player -> names.add(player.getName()));
        else
            players
                    .stream()
                    .filter(player -> player.getName().startsWith(argString))
                    .forEach(player -> names.add(player.getName()));

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
            return Arrays.asList(args);

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

            return  tabCompleted
                    .stream()
                    .filter(arg -> arg.startsWith(args[length - 1]))
                    .collect(Collectors.toList());
        }

        return getDefaultTabList(args);
    }

    public CommandsBuilder setOnlyPlayers(boolean value)
    {
        onlyPlayer = value;
        return this;
    }

    public CommandsBuilder setOnlyConsole(boolean value)
    {
        onlyConsole = value;
        return this;
    }

    public CommandsBuilder setTabComplete(boolean value)
    {
        tabComplete = value;
        return this;
    }

    public CommandsBuilder setUsage(List<String> usage)
    {
        this.usage = new MessageProperties(usage);
        return this;
    }

    public CommandsBuilder setUsage(Enum<?> e)
    {
        this.usage = coolConfig.get(e).getMessageProperties();
        return this;
    }

    public CommandsBuilder setPermission(String message)
    {
        this.permission = message;
        return this;
    }

    public CommandsBuilder setPermission(Enum<?> e)
    {
        this.permission = (String) coolConfig.get(e).getValue();
        return this;
    }

    public void build()
    {
        plugin.getCommand(command).setExecutor(this);

        if (tabComplete)
        {
            plugin.getCommand(command).setTabCompleter(this);
        }

        if (aevyDependent == null)
        {
            aevyCore.getCanReload().add(this);
        }
        else
        {
            aevyDependent.addReloadable(this);
        }
    }

}
package fun.aevy.aevycore.utils.configuration.entries;

import fun.aevy.aevycore.utils.configuration.elements.annotations.Resource;

public class Aevy
{
    private static final String PLUGIN_NAME = "aevycore.";

    @Resource(path = "permissions")
    public enum Perms
    {
        RELOAD
    }

    @Resource(path = "messages")
    public enum Messages
    {
        PREFIX, UNKNOWN_PLAYER, NO_PERMS, NO_PLAYER, NO_CONSOLE
    }

    @Resource(path = PLUGIN_NAME + "aevy-command")
    public enum CommandAevy
    {
        RELOAD, VERSION
    }

    @Resource(path = PLUGIN_NAME + "usages")
    public enum Usages
    {
        AEVY
    }

    @Resource(path = "database")
    public enum Database
    {
        ENABLED, DRIVER, URL, IP, PORT, USER, PASSWORD, DATABASE, MAX_POOL_SIZE, DEBUG, PROPERTIES, USE_DEFAULTS
    }

}
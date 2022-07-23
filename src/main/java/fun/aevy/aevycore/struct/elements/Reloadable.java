package fun.aevy.aevycore.struct.elements;

import fun.aevy.aevycore.utils.configuration.Config;

public abstract class Reloadable
{
    protected Config config;

    public abstract void reload();
}

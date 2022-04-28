package fun.aevy.aevycore.utils.builders;

import fun.aevy.aevycore.AevyCore;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Creates new Managers. An empty set and HashMap are automatically provided.
 * @since 1.0
 * @author Sorridi
 */
@SuppressWarnings("unused")
@Getter
public abstract class ManagerBuilder<G, N>
{
    protected final AevyCore        aevyCore;
    protected final HashSet<G>      set;
    protected final HashMap<G, N>   map;

    /**
     * Constructor for new Managers.
     * @param aevyCore Instance of AevyCore.
     * @since 1.0
     */
    public ManagerBuilder(AevyCore aevyCore)
    {
        this.aevyCore   = aevyCore;
        this.set        = new HashSet<>();
        this.map        = new HashMap<>();
    }

    /**
     * Adds a generic object to the set.
     * @param generic Generic object to be added to the set.
     * @since 1.0
     */
    public void addSet(G generic)
    {
        set.add(generic);
    }

    /**
     * Adds generic objects to the set.
     * @param generic Generic objects to be added to the set.
     * @since 1.0
     */
    public void addSet(List<G> generic)
    {
        generic.forEach(this::addSet);
    }

    /**
     * Removes a generic object to the set.
     * @param generic Generic object to be removed from the set.
     * @since 1.0
     */
    public void removeSet(G generic)
    {
        set.remove(generic);
    }

    /**
     * Removes generic objects from the set.
     * @param generic Generic objects to be removed from the set.
     * @since 1.0
     */
    public void removeSet(List<G> generic)
    {
        generic.forEach(this::removeSet);
    }

    /**
     * Checks if an object is present in the set.
     * @param generic Generic object to be searched.
     * @since 1.3
     */
    public boolean isPresentInSet(G generic)
    {
        return set.contains(generic);
    }

    /**
     * Checks if an object is present in the hashmap's keys.
     * @param genericKey Generic object to be searched.
     * @since 1.3
     */
    public boolean isPresentInMapKey(G genericKey)
    {
        return map.containsKey(genericKey);
    }

    /**
     * Checks if an object is present in the hashmap's values.
     * @param genericValue Generic object to be searched.
     * @since 1.3
     */
    public boolean isPresentInMapValue(N genericValue)
    {
        return map.containsValue(genericValue);
    }

    /**
     * Searches an item using {@link Lambda#expression(Object)} in the set and returns it.
     * @param lambda The expression.
     * @return The item that has been found.
     * @since 1.3
     */
    public <K> G getItem(@NotNull Lambda lambda)
    {
        return set.stream().filter(lambda::expression).findAny().orElse(null);
    }

    /**
     * Gets the value correspondent to the item in the map.
     * @param genericKey Key of the map.
     * @return The value associated to the item in the map.
     * @since 1.3
     */
    public N getValue(G genericKey)
    {
        return map.get(genericKey);
    }

    /**
     * Adds an item to the HashMap.
     * @param genericKey    Generic key.
     * @param genericValue  Generic value.
     * @since 1.0
     */
    public void addMap(G genericKey, N genericValue)
    {
        map.put(genericKey, genericValue);
    }

    /**
     * Replaces an item's value from the map.
     * @param genericKey    Generic key.
     * @param genericValue  Generic new value.
     * @since 1.0
     */
    public void replaceMap(G genericKey, N genericValue)
    {
        map.replace(genericKey, genericValue);
    }

    /**
     * Removes an item from the HashMap.
     * @param genericKey Generic key.
     * @since 1.0
     */
    public void removeMap(G genericKey)
    {
        map.remove(genericKey);
    }

}

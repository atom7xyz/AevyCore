package fun.aevy.aevycore.utils.builders;

import fun.aevy.aevycore.AevyCore;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Creates new Managers. An empty List and HashMap are automatically provided.
 * @since 1.0
 * @author Sorridi
 */
@SuppressWarnings("unused")
@Getter
public abstract class ManagerBuilder<G, N>
{
    protected final AevyCore        aevyCore;
    protected final List<G>         list;
    protected final HashMap<G, N>   map;

    /**
     * Constructor for new Managers.
     * @param aevyCore Instance of AevyCore.
     * @since 1.0
     */
    public ManagerBuilder(AevyCore aevyCore)
    {
        this.aevyCore   = aevyCore;
        this.list       = new ArrayList<>();
        this.map        = new HashMap<>();
    }

    /**
     * Adds a generic object to the list.
     * @param generic Generic object to be added to the list.
     * @since 1.0
     */
    public void addList(G generic)
    {
        list.add(generic);
    }

    /**
     * Adds generic objects to the list.
     * @param generic Generic objects to be added to the list.
     * @since 1.0
     */
    public void addList(List<G> generic)
    {
        generic.forEach(this::addList);
    }

    /**
     * Removes a generic object to the list.
     * @param generic Generic object to be removed from the list.
     * @since 1.0
     */
    public void removeList(G generic)
    {
        list.remove(generic);
    }

    /**
     * Removes generic objects from the list.
     * @param generic Generic objects to be removed from the list.
     * @since 1.0
     */
    public void removeList(List<G> generic)
    {
        generic.forEach(this::removeList);
    }

    /**
     * Checks if an object is present in the list.
     * @param generic Generic objects to search in the list.
     * @since 1.0
     */
    public boolean isPresent(G generic)
    {
        return list
                .stream()
                .anyMatch(g -> g.equals(generic));
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

    public abstract <K> G getFromList(K generic);

    public abstract N getFromMap(G generic);

    public abstract <V, K> boolean equals(V type, K input);

}

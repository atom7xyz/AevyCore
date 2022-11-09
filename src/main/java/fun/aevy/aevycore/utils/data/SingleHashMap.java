package fun.aevy.aevycore.utils.data;

import java.util.HashMap;

public class SingleHashMap<K> extends HashMap<K, K>
{

    public K put(K key)
    {
        return put(key, key);
    }

    public K putIfAbsent(K key)
    {
        return putIfAbsent(key, key);
    }

}
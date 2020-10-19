import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

/*
Кэш реализован на дженериках, чтоб была возможность хранить любые обьекты.
 */
public class Cache<K, V> {
    private final ConcurrentHashMap<Integer, CacheObj> cacheMap = new ConcurrentHashMap<>();

    public String put(K key, V value, long ttl) {
        if (key == null) {
            System.err.println("'key' can't be null!");
        }
        cacheMap.put(key.hashCode(), new CacheObj(value, ttl));
        return "value for key='" + key.toString() + "' is posted to cache with TTL=" + ttl;
    }

    public V get(K key) throws NoSuchElementException {

        CacheObj cacheObj;
        int hashCode = key.hashCode();
        cacheObj = cacheMap.get(hashCode);
        if (cacheObj == null) {
            System.err.println("not found in cache.");
            throw new NoSuchElementException();
        }
        if (cacheObj.bestBefore < System.currentTimeMillis()) {
            System.out.println("expired... must generate new value");
            remove(key);
            throw new NoSuchElementException();
        }
        return cacheObj.value;
    }

    public void remove(K key) {
        cacheMap.remove(key.hashCode());
    }


    private class CacheObj {
        private final V value;
        private final long bestBefore;

        public CacheObj(V value, long ttl) {
            this.value = value;
            this.bestBefore = System.currentTimeMillis() + ttl;
        }
    }
}
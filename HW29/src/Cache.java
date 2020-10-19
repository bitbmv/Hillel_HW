import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

/*
Кэш реализован на дженериках, чтоб была возможность хранить любые обьекты.
 */
public class Cache<K, V> {
    private final ConcurrentHashMap<Integer, CacheObj> cacheMap = new ConcurrentHashMap<>();

    // сохранить в кэше значение ключа с указанием времени жизни
    public String put(K key, V value, long ttl_milliseconds) {
        if (key == null) {
            System.err.println("'key' can't be null!");
        }
        cacheMap.put(key.hashCode(), new CacheObj(value, ttl_milliseconds));
        return "value for key='" + key.toString() + "' is posted to cache with TTL=" + ttl_milliseconds;
    }

    // получить значение по ключу (исключения NoSuchElementException при отсутствии ключа в кэше или при истечении срока годности закешированного значения)
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

    // удалить из кэша значения по ключу
    public void remove(K key) {
        cacheMap.remove(key.hashCode());
    }

    // очисть весь кэш
    public void clearAll() {
        cacheMap.clear();
    }

    // класс для хранения значения и времени до которого это значение актуально
    private class CacheObj {
        private final V value;
        private final long bestBefore;

        public CacheObj(V value, long ttl) {
            this.value = value;
            this.bestBefore = System.currentTimeMillis() + ttl;
        }
    }
}
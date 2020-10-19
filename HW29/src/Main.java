import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Cache cache = new Cache();

        System.out.println(cache.put(1, "111", 1000));
        System.out.println(cache.put("2", 222, 500));
        System.out.println(cache.put(true, null, 500));

        try {
            System.out.print("trying get value for key 1: ");
            System.out.println(cache.get(1));
        } catch (NoSuchElementException e) {
            // code for generating new value...
        }

        try {
            System.out.print("trying get value for key '2': ");
            System.out.println(cache.get("2"));
        } catch (NoSuchElementException e) {
            // code for generating new value...
        }

        try {
            System.out.print("trying get value for key 'true': ");
            System.out.println(cache.get(true));
        } catch (NoSuchElementException e) {
            // code for generating new value...
        }

        System.out.println("sleeping 400ms...");
        Thread.sleep(400);

        try {
            System.out.print("trying get value for key 1: ");
            System.out.println(cache.get(1));
        } catch (NoSuchElementException e) {
            // code for generating new value...
        }

        try {
            System.out.print("trying get value for key '2': ");
            System.out.println(cache.get("2"));
        } catch (NoSuchElementException e) {
            // code for generating new value...
        }

        System.out.println("sleeping 100ms...");
        Thread.sleep(100);

        try {
            System.out.print("trying get value for key 'true': ");
            System.out.println(cache.get(true));
        } catch (NoSuchElementException e) {
            // code for generating new value...
        }

        // пытаемся передать null в качестве ключа
        System.out.print("trying put value with key=null: ");
        cache.put(null, "111", 1000);
    }

}

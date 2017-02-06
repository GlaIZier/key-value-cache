package ru.glaizier;

import org.apache.commons.cli.*;
import ru.glaizier.cache.Cache;
import ru.glaizier.cache.LruCache;
import ru.glaizier.cache.MruCache;
import ru.glaizier.cache.MultiLevelCache;
import ru.glaizier.storage.DiskKeyValueStorage;
import ru.glaizier.storage.MemoryKeyValueStorage;

import java.util.Arrays;
import java.util.Map;

// TODO move all todos to issues on github
public class Main {

    public static void main(String[] args) {
        Options options = createOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;
        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            new HelpFormatter().printHelp("cache", options);
            System.exit(1);
        }

        if (line == null)
            throw new IllegalStateException("Parser returned null value!");

        boolean isLru = true;
        int c1 = 3;
        int c2 = 5;
        String d = "./key-value-cache/";

        String sValue = line.getOptionValue("s");
        if ("mru".equals(sValue))
            isLru = false;

        String c1Value = line.getOptionValue("c1");
        if (c1Value != null)
            c1 = Integer.parseInt(c1Value);

        String c2Value = line.getOptionValue("c2");
        if (c2Value != null)
            c2 = Integer.parseInt(c2Value);

        String dValue = line.getOptionValue("d");
        if (dValue != null && !"".equals(dValue))
            d = dValue;

        testCache(isLru, c1, c2, d);
    }

    // -s: strategy
    // -c: capacity
    // -d: directory
    private static Options createOptions() {
        Options options = new Options();

        Option s = Option.builder("s").argName("lru|mru")
                .desc("Strategy of cache. Can be lru or mru")
                .longOpt("strategy")
                .hasArg()
                .build();

        Option c1 = Option.builder("c1").argName("positive int")
                .desc("Capacity for l1")
                .longOpt("capacity1")
                .hasArg()
                .build();

        Option c2 = Option.builder("c2").argName("positive int")
                .desc("Capacity for l2")
                .longOpt("capacity2")
                .hasArg()
                .build();

        Option d = Option.builder("d").argName("l2 directory")
                .desc("Directory for l2 cache files")
                .longOpt("directory")
                .hasArg()
                .build();

        options.addOption(s);
        options.addOption(c1);
        options.addOption(c2);
        options.addOption(d);
        return options;
    }

    private static void testCache(boolean isLru, int c1, int c2, String d) {
        System.out.println(String.format("Parameters for cache: isLru %s, c1 %s, c2 %s, d %s", isLru, c1, c2, d));
        Cache<Integer, String> cache;
        if (isLru)
            cache = new MultiLevelCache<>(
                    Arrays.asList(
                            new LruCache<>(new MemoryKeyValueStorage<>(), c1),
                            new LruCache<>(new DiskKeyValueStorage<>(d, Integer.class, String.class), c2)
                    )
            );
        else
            cache = new MultiLevelCache<>(
                    Arrays.asList(
                            new MruCache<>(new MemoryKeyValueStorage<>(), c1),
                            new MruCache<>(new DiskKeyValueStorage<>(d, Integer.class, String.class), c2)
                    )
            );

        System.out.println("Cache has been created");
        System.out.println("Putting to cache 50 values...");

        for (int i = 0; i < 50; i++) {
            Map.Entry<Integer, String> e = cache.putAndGetEvicted(i, String.valueOf(i));
            System.out.println(String.format("Putting to cache key-value %s-%s. Evicted key-value %s-%s", i, i,
                    e == null ? null : e.getKey(), e == null ? null : e.getValue()));
        }

//        System.out.println("Erasing all values...");
//        int size = cache.getSize();
//        for (int i = 0; i < size; i++) {
//            Map.Entry<Integer, String> e = cache.evict();
//            System.out.println(String.format("Evicted key-value %s-%s",
//                    e == null ? null : e.getKey(), e == null ? null : e.getValue()));
//        }
    }

}

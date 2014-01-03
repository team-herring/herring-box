package org.herring.box.client.whatch;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WatcherManager {
    private static Map<String, Watcher> watcherMap = new HashMap<>();

    public synchronized static boolean add(String directoryPath) throws Exception {
        if (watcherMap.containsKey(directoryPath)){
            return false;
        }

        isDirectory(directoryPath);

        watcherMap.put(directoryPath, Watcher.createAndStart(directoryPath));

        return true;
    }

    public synchronized static boolean remove(String directoryPath) throws IOException {
        if (!watcherMap.containsKey(directoryPath)){
            return false;
        }

        isDirectory(directoryPath);

        Watcher watcher = watcherMap.get(directoryPath);
        watcher.stop();

        watcherMap.remove(directoryPath);

        return true;
    }

    private static void isDirectory(String directoryPath) throws IOException {
        File file = new File(directoryPath);
        if (!file.isDirectory()){
            throw new IOException("is not directory");
        }
    }
}

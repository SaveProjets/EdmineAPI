package fr.edminecoreteam.api.utils;

import lombok.Getter;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThread {
    @Getter
    public static final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public static void runThreadPoolTask(Runnable task) {
        threadPool.submit(task);
    }
    public static void runThreadPoolTask(Callable<Objects> callable) {
        threadPool.submit(callable);
    }
}

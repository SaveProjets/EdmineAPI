package fr.edmine.api.utils;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThread
{
	public static final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

	public static void runThreadPoolTask(Runnable task)
	{
		threadPool.submit(task);
	}

	public static void runThreadPoolTask(Callable<Objects> callable)
	{
		threadPool.submit(callable);
	}
	
	public static ExecutorService getThreadPool()
	{
		return threadPool;
	}
}

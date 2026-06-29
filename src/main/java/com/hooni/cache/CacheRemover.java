package com.hooni.cache;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class CacheRemover extends Thread
{

	public CacheRemover(ConcurrentHashMap<String, CacheObject> cache)
	{
		_cacheMap = cache;
	}
	
	public void run()
	{

		for(;;)
		{
			lock.lock();
			try
			{
				Set<Map.Entry<String,CacheObject>> cacheItems = _cacheMap.entrySet();

				for (Map.Entry<String,CacheObject> item : Collections.synchronizedSet(cacheItems))
				{
					CacheObject co = (CacheObject)item.getValue();
					if (co.getExpiredTime() < System.currentTimeMillis())
					{
						System.out.println("removing item " + item.getKey());
						_cacheMap.remove(item.getKey());
					}
				}
				try
				{
					Thread.sleep(INTERVAL_TO_CHECK_CACHE);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			finally
			{
				lock.unlock();
			}
		}
	}
	
	//private static final long INTERVAL_TO_CHECK_CACHE = 2 * 1000;
	
	private static final long INTERVAL_TO_CHECK_CACHE = 15 * 60 * 1000; //60 minutes
	
	//private static final long INTERVAL_TO_CHECK_CACHE = 60 * 60 * 1000; //60 minutes
	
	private final ReentrantLock lock = new ReentrantLock();
	
	private ConcurrentHashMap<String, CacheObject> _cacheMap;
}

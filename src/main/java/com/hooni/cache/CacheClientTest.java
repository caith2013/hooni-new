package com.hooni.cache;



public class CacheClientTest {

	public static void main(String[] args)
	{
		System.out.println("you are here");
		try
		{
			CacheConnectionPool pool = new CacheConnectionPool(32);

			for (int i =0; i < 5; i++)
			{
			HooniCache cache = pool.borrow();
			cache.put("test", "test data", 5*1000);

			cache.put("test", "add some data to the cache", 4*1000);
			
			System.out.println("read from the cache: " + cache.get("test"));
			
			cache.put("test1", "another some data to the cache", 3*1000);
			
			cache.put("test2", "second message", 5*1000);
			/*
			cache.put("testdb", new ProductSQLs().getProducts(0, 6));
			
			cache.put("foods", new FoodSQLs().getFoods(0, 6));
			*/
			//cache.close();
				pool.release(cache);
			counter++;
			//System.out.println("so far the cache number is: " + counter);
			}
		}
		catch(Exception e)
		{
			System.out.println("fail here with total cache: " + counter);
			e.printStackTrace();
		}
		

	}
	
	
	
	private static int counter;
	

}

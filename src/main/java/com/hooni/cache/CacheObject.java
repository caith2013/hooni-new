package com.hooni.cache;




public class CacheObject<T> implements java.io.Serializable
{
	public CacheObject(T o)
	{
		this(o,DEFAULT_EXPIRATION_TIME);
	}

	public CacheObject(T o, long time)
	{
		_data = o;
		_expiredTime = time;
		
	}
	public long getExpiredTime(){ return _expiredTime; }
	public void setExpiredTime(long time) { _expiredTime = time; }
	
	public T getData() { return _data; }
	public void setData(T data) { _data = data; }
	
	private T _data;
	private long _expiredTime;
	static final long DEFAULT_EXPIRATION_TIME = 15 * 60 * 1000; //15 minutes
	
	private static final long serialVersionUID = 7471604174088393365L;

}

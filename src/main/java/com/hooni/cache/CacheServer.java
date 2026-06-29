package com.hooni.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public final class CacheServer extends Thread
{
	private CacheServer() throws IOException
	{

		_serverSocket = new ServerSocket();
		_serverSocket.setReuseAddress(true);
		_serverSocket.bind(new InetSocketAddress(_host,_port), 1000);

	}
	
	public static void main(String[] args) throws IOException
	{
		CacheServer cacheServer = CacheServer.getInstance();
		cacheServer.start();

		Runtime.getRuntime().addShutdownHook(new Thread(cacheServer::shutdown));

	}

	public void shutdown(){
		_running = false;
        try
		{
            _serverSocket.close();
        }
		catch (IOException e)
		{
            throw new RuntimeException(e);
        }
        _pool.shutdown();
	}

	public void run() {
		while (_running) {
			try {
				Socket incoming = _serverSocket.accept();
				_pool.submit(new ServeClientAgent(incoming, _cacheMap));
			} catch (IOException e) {
				if (!_running) break;
			}
		}
	}
	

	
	public ServerSocket getServerSocket()
	{
		return _serverSocket;
	}
	
	
	
	public Cache<String, CacheObject> getCacheMap()
	{
		return _cacheMap;
	}
	
	public long size()
	{
		return _cacheMap.estimatedSize();
	}
	
	public void clear()
	{
		_cacheMap.invalidateAll();
	}

	public static synchronized CacheServer getInstance() throws IOException {
		if (_cacheServerInstance == null) {
			_cacheServerInstance = new CacheServer();
		}
		return _cacheServerInstance;
	}


	private final ServerSocket _serverSocket;
	
	//private static final  ConcurrentHashMap<String, CacheObject> _cacheMap = new ConcurrentHashMap<String, CacheObject>();
	private static final Cache<String, CacheObject> _cacheMap =
			Caffeine.newBuilder()
					.expireAfterWrite(10, TimeUnit.MINUTES)
					.maximumSize(10_000)
					.build();

	private final ExecutorService _pool = Executors.newFixedThreadPool(32);
	
	private static CacheServer _cacheServerInstance;
	static String _host = "localhost";
	static int _port = 3001;

	private volatile boolean _running = true;
	
}

package com.hooni.cache;

import com.github.benmanes.caffeine.cache.Cache;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServeClientAgent implements Runnable {

	private final Socket incoming;
	private final ObjectInputStream in;
	private final ObjectOutputStream out;
	private final Cache<String, CacheObject> cache;

	public ServeClientAgent(Socket s, Cache<String, CacheObject> cache) throws IOException {
		this.incoming = s;
		this.cache = cache;
		this.in = new ObjectInputStream(incoming.getInputStream());
		this.out = new ObjectOutputStream(incoming.getOutputStream());
	}

	@Override
	public void run() {
		System.out.println("Serving client on " + Thread.currentThread().getName());
		try {
			while (true) {
				CacheServerMsg msg = (CacheServerMsg) in.readObject();
				handle(msg);
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Client disconnected: " + e.getMessage());
		} finally {
			closeQuietly();
			System.out.println("Finished serving client: " + Thread.currentThread().getName());
		}
	}

	private void handle(CacheServerMsg msg) throws IOException {
		char access = msg.getCommand();
		switch (access) {
			case 'w': {
				cache.put(msg.getKey(), msg.getcObject());
				break;
			}
			case 'r': {
				CacheObject value = cache.getIfPresent(msg.getKey());
				out.writeObject(value);
				out.flush();
				break;
			}
			case 'm': {
				cache.invalidate(msg.getKey());
				break;
			}
			case 'x':
			{
				cache.cleanUp();
				break;
			}
			default:
				System.out.println("Unknown command: " + access);
		}
	}

	private void closeQuietly() {
		try { in.close(); } catch (IOException ignored) {}
		try { out.close(); } catch (IOException ignored) {}
		try { incoming.close(); } catch (IOException ignored) {}
	}
}

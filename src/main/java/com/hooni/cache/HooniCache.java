package com.hooni.cache;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HooniCache {

		private Socket socket;
		private ObjectInputStream in;
		private ObjectOutputStream out;

		public HooniCache() {
			connect();
		}

		private synchronized void connect() {
			try {
				socket = new Socket();
				socket.setReuseAddress(true);
				socket.connect(new InetSocketAddress(CacheServer._host, CacheServer._port), 2000);
				socket.setSoTimeout(2000);

				out = new ObjectOutputStream(socket.getOutputStream());
				in  = new ObjectInputStream(socket.getInputStream());

			} catch (IOException e) {
				throw new RuntimeException("Failed to connect to cache server", e);
			}
		}

		private synchronized void send(CacheServerMsg msg) throws IOException {
			try {
				out.writeObject(msg);
				out.flush();
			} catch (IOException e) {
				reconnect();
				out.writeObject(msg);
				out.flush();
			}
		}

		synchronized void reconnect() {
			close();
			connect();
		}

		public synchronized Object get(String key) {
			try {
				send(new CacheServerMsg('r', key));
				CacheObject co = (CacheObject) in.readObject();
				return co == null ? null : co.getData();
			} catch (Exception e) {
				reconnect();
				return null;
			}
		}

		public synchronized Long getExpirationTime(String key) {
			try {
				send(new CacheServerMsg('t', key)); // NEW COMMAND
				CacheObject co = (CacheObject) in.readObject();
				return co == null ? null : co.getExpiredTime();
			} catch (Exception e) {
				reconnect();
				return null;
			}
		}

		public synchronized void put(String key, Object value, long ttl) {
			try {
				long expireAt = System.currentTimeMillis() + ttl;
				send(new CacheServerMsg('w', key, new CacheObject(value, expireAt)));
			} catch (Exception e) {
				reconnect();
			}
		}
	public synchronized void put(String key, Object value) {
		try {
			long expireAt = System.currentTimeMillis() + 6 * 10000; // Default TTL of one minute
			send(new CacheServerMsg('w', key, new CacheObject(value, expireAt)));
		} catch (Exception e) {
			reconnect();
		}
	}

		public synchronized void remove(String key) {
			try {
				send(new CacheServerMsg('m', key));
			} catch (Exception e) {
				reconnect();
			}
		}

		public synchronized void close() {
			try { if (in != null) in.close(); } catch (IOException ignored) {}
			try { if (out != null) out.close(); } catch (IOException ignored) {}
			try { if (socket != null) socket.close(); } catch (IOException ignored) {}
		}


	public synchronized boolean isHealthy() {
		try {
			send(new CacheServerMsg('p')); // ping
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}

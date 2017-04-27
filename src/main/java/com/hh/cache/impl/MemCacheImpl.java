package com.hh.cache.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.danga.MemCached.MemCachedClient;
import com.hh.cache.inf.ICache;
import com.hh.system.util.CallBack;

public class MemCacheImpl<T> implements ICache<T> {
	private MemCachedClient memCache;
	private String name;
	private Class<T> class1;

	public MemCacheImpl(MemCachedClient memCache, String name,Class<T> class1) {
		this.memCache = memCache;
		this.name = name;
		this.class1=class1;
	}

	public String getName() {
		return name;
	}

	@Override
	public Map<String, Long> getCacheMap() {
		String cacheName = getName();
		Map<String, Long> map = (Map<String, Long>) this.memCache
				.get(cacheName);
		if (map == null) {
			map = new HashMap<String, Long>();
			memCache.set(cacheName, map);
		}
		return map;
	}

	@Override
	public void saveCacheMap(Map<String, Long> map) {
		memCache.set(getName(), map);
	}

	@Override
	public void put(String key, T value) {
		key = getName() + "_" + key;
		memCache.set(key, value);
		Map<String, Long> map = getCacheMap();
		map.put(key, Long.valueOf(System.currentTimeMillis()));
		saveCacheMap(map);
	}

	@Override
	public void clear() {
		Map<String, Long> map = getCacheMap();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			memCache.delete(key);
		}
		map.clear();
		saveCacheMap(map);
	}

	@Override
	public void remove(String key) {
		key = getName() + "_" + key;
		
		memCache.delete(key);
		Map<String, Long> map = getCacheMap();
		map.remove(key);
		saveCacheMap(map);
	}

	@Override
	public T get(String key, CallBack callback) {
		Object object = memCache.get(getName()+"_"+key);
		if (object == null) {
			if (callback != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", key);
				map.put("cacheName", name);
				map.put("class", class1);
				object = callback.execute(map);
				if (object != null) {
					put(key, (T) object);
					return (T) object;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return (T) object;
		}
	}

	@Override
	public T get(String key) {
		return get(key, null);
	}

	@Override
	public void remove(List<String> keys) {
		for (String string : keys) {
			remove(string);
		}
	}

	static {
		// String poolName = BaseSystemUtil.systemMap.get("mencachePoolName");
		// memCache = new MemCachedClient();
		// 设置缓存服务器列表，当使用分布式缓存的时，可以指定多个缓存服务器。这里应该设置为多个不同的服务，我这里将两个服务设置为一样的，大家不要向我学习，呵呵。
		// String[] servers = { "10.15.0.215:46697"
		// // "server3.mydomain.com:1624"
		// };
		// // 设置服务器权重
		// // Integer[] weights = { 3, 2 };
		// Integer[] weights = { 1 };
		// // 创建一个Socked连接池实例
		// SockIOPool pool = SockIOPool.getInstance();
		// // 向连接池设置服务器和权重
		// pool.setServers(servers);
		// pool.setWeights(weights);
		// pool.setNagle(false);
		// pool.setSocketTO(3000);
		// pool.setSocketConnectTO(0);
		// pool.initialize();
	}

	public static void main(String[] args) {
		// memCache.set("foo", "This is a test String");
		// String bar = memCache.get("foo").toString();
		// System.out.println(">>> " + bar);
	}

}

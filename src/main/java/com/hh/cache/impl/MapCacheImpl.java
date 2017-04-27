package com.hh.cache.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.danga.MemCached.MemCachedClient;
import com.hh.cache.inf.ICache;
import com.hh.system.util.CallBack;

public class MapCacheImpl<T> implements ICache<T> {

	private Map<String, Object> memCache;
	private String name;
	private Class<T> class1;

	public MapCacheImpl(Map<String, Object> memCache, String name,Class<T> class1) {
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
			memCache.put(cacheName, map);
		}
		return map;
	}

	@Override
	public void saveCacheMap(Map<String, Long> map) {
		memCache.put(getName(), map);
	}

	@Override
	public void put(String key, T value) {
		key = getName() + "_" + key;
		
		memCache.put(key, value);
		Map<String, Long> map = getCacheMap();
		map.put(key, Long.valueOf(System.currentTimeMillis()));
		saveCacheMap(map);
	}

	@Override
	public void clear() {
		Map<String, Long> map = getCacheMap();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			memCache.remove(key);
		}
		map.clear();
		saveCacheMap(map);
	}

	@Override
	public void remove(String key) {
		key = getName() + "_" + key;
		
		memCache.remove(String.valueOf(key));
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

}

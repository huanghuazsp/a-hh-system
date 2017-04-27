package com.hh.cache.inf;

import java.util.List;
import java.util.Map;

import com.hh.system.util.CallBack;

public interface ICache<T> {
	public void put(String key, T value);

	public T get(String key, CallBack callback);

	public T get(String key);

	public void clear();

	public void remove(String key);
	public void remove(List<String> keys);

	public Map<String, Long> getCacheMap();

	public void saveCacheMap(Map<String, Long> map);
}

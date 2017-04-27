package com.hh.cache.impl;

import java.util.HashMap;
import java.util.Map;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.hh.cache.inf.ICache;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.StaticVar;

public class CacheFactory {
	public static MemCachedClient memCache = null;
	private static Map<String, Object> mapCache;
	public static Map<String, ICache> cacheImplMap = new HashMap<String, ICache>();;
	static {
		String cache = StaticVar.systemProperties.get("hh.cache");
		if ("memcache".equals(cache)) {
			memcacheinit();
		} else {
			mapCache=new HashMap<String, Object>();
		}
	}

	public static <T> ICache<T> getCache(Class<T> module, String name) {
		ICache<T> iCache;
		if (cacheImplMap.get(name) != null) {
			return cacheImplMap.get(name);
		}
		if (memCache != null) {
			iCache = new MemCacheImpl<T>(memCache, name,module);
		} else {
			iCache = new MapCacheImpl<T>(mapCache, name,module);
		}
		cacheImplMap.put(name, iCache);
		return iCache;
	}
	public static  ICache<Object> getCache(String name) {
		return getCache(Object.class, name);
	}
	public static <T> ICache<T> getCache(Class<T> module) {
		return getCache(module, module.getName());
	}

	private static void memcacheinit() {
		memCache = new MemCachedClient(Check.getEmptyDefaultValue(
				StaticVar.systemProperties.get("hh.poolName"), ""));
		// 获取连接池的实例
		SockIOPool pool = SockIOPool.getInstance(Check.getEmptyDefaultValue(
				StaticVar.systemProperties.get("hh.poolName"), ""));
		// 服务器列表及其权重
		String[] servers = Check.getEmptyDefaultValue(
				StaticVar.systemProperties.get("hh.servers"), "").split(",");

		String[] weightstr = Check.getEmptyDefaultValue(
				StaticVar.systemProperties.get("hh.weights"), "3").split(",");
		Integer[] weights = new Integer[weightstr.length];
		for (int i = 0; i < weightstr.length; i++) {
			String string = weightstr[i];
			weights[i] = Convert.toInt(string);
		}
		// 设置服务器信息
		pool.setServers(servers);
		pool.setWeights(weights);
		// 设置初始连接数、最小连接数、最大连接数、最大处理时间
		pool.setInitConn(Check.getEmptyDefaultValue(
				Convert.toInt(StaticVar.systemProperties.get("hh.InitConn")), 50));
		pool.setMinConn(Check.getEmptyDefaultValue(
				Convert.toInt(StaticVar.systemProperties.get("hh.MinConn")), 50));
		pool.setMaxConn(Check.getEmptyDefaultValue(
				Convert.toInt(StaticVar.systemProperties.get("hh.MaxConn")), 1000));
		pool.setMaxIdle(Check.getEmptyDefaultValue(
				Convert.toInt(StaticVar.systemProperties.get("hh.MaxIdle")),
				1000 * 60 * 60));
		// 设置连接池守护线程的睡眠时间
		pool.setMaintSleep(Check.getEmptyDefaultValue(
				Convert.toInt(StaticVar.systemProperties.get("hh.MaintSleep")),
				60));
		// 设置TCP参数，连接超时
		pool.setNagle(Check.getEmptyDefaultValue(
				Convert.toBoolean(StaticVar.systemProperties.get("hh.Nagle")),
				false));
		pool.setSocketTO(Check.getEmptyDefaultValue(
				Convert.toInt(StaticVar.systemProperties.get("hh.SocketTO")), 60));
		pool.setSocketConnectTO(0);
		// 初始化并启动连接池
		pool.initialize();
		memCache.flushAll(servers);
	}
}

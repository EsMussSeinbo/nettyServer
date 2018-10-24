package com.hisense.nettyServer.server;

import redis.clients.jedis.JedisCluster;

/**
 * 
 * @author qiush
 *
 */
public interface DataSupplementServer {
	
	public void dataSupplementServer(String time1,String time2,String deviceId,String dataType,JedisCluster jedisCluster);
}

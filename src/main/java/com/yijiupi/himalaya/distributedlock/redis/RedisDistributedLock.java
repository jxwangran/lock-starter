/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.redis;

import java.util.Arrays;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * @ClassName: RedisDistributedLock
 * @Description:
 * @author wangran
 * @date 2018年9月11日 上午10:12:06
 * 
 */
//@Component
public class RedisDistributedLock extends AbstractRedisDistributedLock {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	private DefaultRedisScript<Boolean> redisLockScript;
	private DefaultRedisScript<Boolean> redisUnLockScript;
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisDistributedLock.class);
	private static final Integer DEFAULT_KEY_NUMS = 1;

	@PostConstruct
	public void init() {
		redisLockScript = new DefaultRedisScript<>();
		redisLockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/redisLock.lua")));
		redisLockScript.setResultType(Boolean.class);

		redisUnLockScript = new DefaultRedisScript<>();
		redisUnLockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/redisUnLock.lua")));
		redisUnLockScript.setResultType(Boolean.class);
	}

	@Override
	public boolean lock(String key, String... params) {
		try {
			Long result = redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					Object nativeConnection = connection.getNativeConnection();
					if (nativeConnection instanceof JedisCluster) {
						return (Long) ((JedisCluster) nativeConnection).eval(redisLockScript.getScriptAsString(),
								Collections.singletonList(key), Arrays.asList(params));
					}
					
					else if (nativeConnection instanceof Jedis) {
						String[] tmpParams = new String[params.length + 1];
						tmpParams[0] = key;
						for (int i = 0; i < tmpParams.length; i++) {
							tmpParams[i + 1] = params[i];
						}
						return (Long) ((Jedis) nativeConnection).eval(redisLockScript.getScriptAsString(),
								DEFAULT_KEY_NUMS, tmpParams);
					}
					return -1L;
				}
			});
			if (result == 0L) {
				throw new RuntimeException("已经加锁");
			}
			if (result == -1L) {
				throw new RuntimeException("系统异常");
			}
		} catch (Exception e) {
			LOGGER.error("RedisDistributedLock lock 加锁异常 key : " + key, e);
			throw new RuntimeException("系统异常！");
		}
		return true;
	}

	@Override
	public boolean releaseLock(String key) {
		try {
			Long result = redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					Object nativeConnection = connection.getNativeConnection();
					if (nativeConnection instanceof JedisCluster) {
						return (Long) ((JedisCluster) nativeConnection).eval(redisUnLockScript.getScriptAsString(),
								DEFAULT_KEY_NUMS, key);
					} else if (nativeConnection instanceof Jedis) {
						return (Long) ((Jedis) nativeConnection).eval(redisUnLockScript.getScriptAsString(),
								DEFAULT_KEY_NUMS, key);
					}
					return -1L;
				}
			});
			if (result == 0L) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("RedisDistributedLock lock 加锁异常 key : " + key, e);
			throw new RuntimeException("系统异常！");
		}
		return false;
	}

}

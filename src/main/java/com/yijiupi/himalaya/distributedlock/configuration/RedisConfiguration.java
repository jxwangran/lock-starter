/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.configuration;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import com.yijiupi.himalaya.distributedlock.redis.RedisDistributedLock;

/** 
* @ClassName: RedisConfiguration 
* @Description: 
* @author wangran
* @date 2018年9月12日 下午2:52:30 
*  
*/
//@Configuration
public class RedisConfiguration {

	@Bean(name = {"redisTemplate"})
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		RedisTemplate template = new RedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}
	
	@Bean
	public RedisDistributedLock getRedisDistributedLock(RedisTemplate redisTemplate) {
		DefaultRedisScript<Boolean> redisLockScript = new DefaultRedisScript<>();
		redisLockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/redisLock.lua")));
		redisLockScript.setResultType(Boolean.class);

		DefaultRedisScript<Boolean> redisUnLockScript = new DefaultRedisScript<>();
		redisUnLockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/redisUnLock.lua")));
		redisUnLockScript.setResultType(Boolean.class);
		RedisDistributedLock lock = new RedisDistributedLock(redisTemplate, redisLockScript, redisUnLockScript);
		return lock;
	}
	
}

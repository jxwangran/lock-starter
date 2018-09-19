/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.yijiupi.himalaya.distributedlock.SupplyDistributedLock;
import com.yijiupi.himalaya.distributedlock.redis.RedisLock;

/**
 * @ClassName: DistributedLockAspectConfiguration
 * @Description: 注解切面
 * @author wangran
 * @date 2018年9月11日 上午11:08:38
 * 
 */
@Aspect
@Configuration
@ConditionalOnClass(SupplyDistributedLock.class)
public class DistributedLockAspectConfiguration {

	@Autowired
	private SupplyDistributedLock distributedLock;
	private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLockAspectConfiguration.class);

	@Pointcut("@annotation(com.yijiupi.himalaya.distributedlock.redis.RedisLock)")
	private void lockPoint() {

	}

	@Around("lockPoint()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		RedisLock redisLock = method.getAnnotation(RedisLock.class);
		String key = redisLock.value();
		if (StringUtils.isEmpty(key)) {
			Object[] args = pjp.getArgs();
			key = Arrays.toString(args);
		}
//		int retryTimes = redisLock.action().equals(LockFailAction.CONTINUE) ? redisLock.retryTimes() : 0;
		boolean lock = distributedLock.lock(key, redisLock.value(), redisLock.expireMills());
		if (!lock) {
			LOGGER.debug("get lock failed : " + key);
			return null;
		}

		// 得到锁,执行方法，释放锁
		LOGGER.debug("get lock success : " + key);
		try {
			return pjp.proceed();
		} catch (Exception e) {
			LOGGER.error("执行方法失败", e);
			throw e ;
		} finally {
			boolean releaseResult = distributedLock.releaseLock(key);
			LOGGER.debug("release lock : " + key + (releaseResult ? " success" : " failed"));
		}
	}

}

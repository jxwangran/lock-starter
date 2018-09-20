/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import com.yijiupi.himalaya.distributedlock.lock.SupplyDistributedLock;
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
		String key = redisLock.key();
		
		String conditions = redisLock.conditions();
		
		List<String> secKeys = parse(conditions, method, pjp.getArgs());
		
		key = key + ":" + secKeys.stream().findFirst().get();
		
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
	
	private String[] getArgParams(Method method) {
		ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
		return parameterNameDiscoverer.getParameterNames(method);
	}
	
	private List<String> parse(String key, Method method, Object[] arguments) {
		ExpressionParser parser = new SpelExpressionParser();
		Expression expression = parser.parseExpression(key);
		StandardEvaluationContext context = new StandardEvaluationContext();
		String[] parameterNames = getArgParams(method);
		for (int i = 0; i < parameterNames.length; i++) {
			context.setVariable(parameterNames[i], arguments[i]);
        }
		Object obj = expression.getValue(context);
		return convertToStrList(obj);
	}
	
	private List<String> convertToStrList(Object obj) {
		if (obj instanceof List) {
			return handleList(obj);
		}
		return Collections.singletonList(String.valueOf(obj));
	}
	
	private List<String> handleList(Object obj) {
		Object first = ((List<?>) obj).stream().findFirst().get();
		if (first instanceof String) {
			return (List<String>) obj;
		}
		return ((List<?>) obj).stream().map(o -> String.valueOf(o)).collect(Collectors.toList());
	}
	
}

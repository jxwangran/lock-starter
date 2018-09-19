/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: RedisLock
 * @Description:
 * @author wangran
 * @date 2018年9月11日 上午10:56:39
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisLock {
	/** 
	 * 锁的资源，redis的key 
	 **/
	String value() default "default";
	
	/**
	 * 加锁资源列表
	 */
	String[] values() default "default";

	/** 
	 * 持锁时间,单位毫秒 
	 */
	long expireMills() default 3000;

	/** 
	 * 当获取失败时候动作 
	 */
	LockFailAction action() default LockFailAction.CONTINUE;

	/** 
	 * 重试的间隔时间,设置GIVEUP忽略此项 
	 */
	long sleepMills() default 200;

	/** 
	 * 重试次数 
	 */
	int retryTimes() default 5;

	public enum LockFailAction {
		/** 放弃 */
		GIVEUP,
		/** 继续 */
		CONTINUE;
	}

}

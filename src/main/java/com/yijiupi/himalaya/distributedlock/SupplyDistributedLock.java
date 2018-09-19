/**   
 * Copyright © 2018 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock;

/** 
* @ClassName: DistributedLock 
* @Description: 现知有Zookipper和Redis分布式锁，为了方便拓展，故定义最上层接口
* @author wangran
* @date 2018年9月11日 上午10:00:01 
*  
*/
public interface SupplyDistributedLock {
	/**
	 * 过期时间：毫秒级别
	 */
	public static final Long DEFAULT_EXPIRE = 500L;
	/**
	 * 默认value
	 */
	public static final String DEFAULT_VALUE = "-1";
	
	/**
	 * 
	 * @Description: 
	 * @date 2018年9月11日 上午10:06:26
	 * @author wangran
	 */
	boolean lock(String key);
	
	/**
	 * 
	 * @Description: expire 过期时间，毫秒级别
	 * @date 2018年9月11日 上午10:06:31
	 * @author wangran
	 */
	boolean lock(String key, String value, Long expire);
	
	/**
	 * 
	 * @Description: 释放锁
	 * @date 2018年9月11日 上午10:06:51
	 * @author wangran
	 */
	boolean releaseLock(String key);
}

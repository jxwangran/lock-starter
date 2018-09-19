/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.redis;

import com.yijiupi.himalaya.distributedlock.lock.SupplyDistributedLock;

/** 
* @ClassName: AbstractDistributedLock 
* @Description: 
* @author wangran
* @date 2018年9月11日 上午10:08:49 
*  
*/
public abstract class AbstractRedisDistributedLock implements SupplyDistributedLock {

	@Override
	public boolean lock(String key) {
		return lock(key, DEFAULT_VALUE, DEFAULT_EXPIRE);
	}
	
	@Override
	public boolean lock(String key, String value, Long expire) {
		String[] params = new String[]{value, String.valueOf(expire)};
		return lock(key, params);
	}
	
	@Override
	public boolean lockBatch(String... keys) {
		return lockBatch(DEFAULT_VALUE, DEFAULT_EXPIRE, keys);
	}
	
	public abstract boolean lock(String key, String... params);

}

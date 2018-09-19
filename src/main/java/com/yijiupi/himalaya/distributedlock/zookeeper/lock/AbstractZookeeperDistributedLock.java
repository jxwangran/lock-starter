/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.zookeeper.lock;

import com.yijiupi.himalaya.distributedlock.SupplyDistributedLock;

/** 
* @ClassName: AbstractZokkeeperDistributedLock 
* @Description: 
* @author wangran
* @date 2018年9月11日 上午10:43:41 
*  
*/
public abstract class AbstractZookeeperDistributedLock implements SupplyDistributedLock {

	@Override
	public boolean lock(String key) {
		return lock(key, DEFAULT_VALUE, DEFAULT_EXPIRE);
	}

}

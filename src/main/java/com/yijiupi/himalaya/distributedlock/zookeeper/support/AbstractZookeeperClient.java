/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.zookeeper.support;

import com.yijiupi.himalaya.distributedlock.zookeeper.ZookeeperClient;

/** 
* @ClassName: AbstractZookeeperClient 
* @Description: 
* @author wangran
* @date 2018年9月11日 下午6:48:46 
*  
*/
public abstract class AbstractZookeeperClient implements ZookeeperClient {

	public void create(String path, boolean ephemeral) {
		if (ephemeral) {
			createEphemeral(path);
		} else {
			createPersistent(path);
		}
	}
	
	protected abstract void createPersistent(String path);

	protected abstract void createEphemeral(String path);
	
}

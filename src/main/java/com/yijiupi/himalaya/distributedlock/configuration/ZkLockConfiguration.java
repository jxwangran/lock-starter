/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.yijiupi.himalaya.distributedlock.zookeeper.lock.ZookeeperDistributedLock;
import com.yijiupi.himalaya.distributedlock.zookeeper.zkclient.ZkClientZookeeperClient;

/**
 * @ClassName: ZkLockConfiguration
 * @Description:
 * @author wangran
 * @date 2018年9月19日 上午11:53:16
 * 
 */
public class ZkLockConfiguration {

	@Bean
	public ZookeeperDistributedLock getZookeeperDistributedLock(@Value("${dubbo.zookeeper.address}") String addresses) {
		ZkClientZookeeperClient zkClinet = new ZkClientZookeeperClient(addresses);
		ZookeeperDistributedLock client = new ZookeeperDistributedLock(zkClinet);
		return client;
	}

}

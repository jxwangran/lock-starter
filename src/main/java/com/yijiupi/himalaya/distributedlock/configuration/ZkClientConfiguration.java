/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yijiupi.himalaya.distributedlock.zookeeper.curator.CuratorZookeeperClient;
import com.yijiupi.himalaya.distributedlock.zookeeper.zkclient.ZkClientZookeeperClient;

/** 
* @ClassName: ZkClientConfiguration 
* @Description: 
* @author wangran
* @date 2018年9月11日 下午8:00:04 
*  
*/
//@Configuration
//@ConditionalOnProperty(prefix = "zkType", name = {"enable"}, havingValue = "true")
public class ZkClientConfiguration {

	@Bean
//	@ConditionalOnExpression("${zkType.type} != 1")
	public ZkClientZookeeperClient getZkClientZookeeperClient(@Value("${dubbo.zookeeper.address}") String addresses) {
		ZkClientZookeeperClient client = new ZkClientZookeeperClient(addresses);
		return client;
	}
	
//	@Bean
//	@ConditionalOnExpression("${zkType.type} == 1")
//	public CuratorZookeeperClient getCuratorZookeeperClient(@Value("${dubbo.zookeeper.address}") String addresses) {
//		CuratorZookeeperClient client = new CuratorZookeeperClient(addresses);
//		return client;
//	}
	
	
}

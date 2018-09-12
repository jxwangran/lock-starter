/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.redission;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** 
* @ClassName: RedissionConfiguration 
* @Description: 
* @author wangran
* @date 2018年9月12日 下午2:11:50 
*  
*/
@Configuration
public class RedissionConfiguration {

//	@Bean
//	public RedissonClient getRedission(@Value("${spring.redis.cluster.nodes}") String redis) {
//		Config config = new Config();
//		config.useClusterServers().addNodeAddress("");
//		RedissonClient redissionClient = Redisson.create(config);
//		return redissionClient;
//	}
	
}

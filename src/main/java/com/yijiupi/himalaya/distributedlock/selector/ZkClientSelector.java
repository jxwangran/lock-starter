/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.yijiupi.himalaya.distributedlock.configuration.EnableZkClient;
import com.yijiupi.himalaya.distributedlock.enums.ZookeeperClientType;
import com.yijiupi.himalaya.distributedlock.zookeeper.curator.CuratorZookeeperClient;
import com.yijiupi.himalaya.distributedlock.zookeeper.zkclient.ZkClientZookeeperClient;

/** 
* @ClassName: ZkClientSelector 
* @Description: 
* @author wangran
* @date 2018年9月11日 下午7:51:46 
*  
*/
public class ZkClientSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Class<?> annotationType = EnableZkClient.class;
		AnnotationAttributes attributes = AnnotationAttributes
				.fromMap(importingClassMetadata.getAnnotationAttributes(annotationType.getName(), false));
		if (attributes == null) {
			throw new IllegalArgumentException(String.format("@%s is not present on importing class '%s' as expected",
					annotationType.getSimpleName(), importingClassMetadata.getClassName()));
		}
		ZookeeperClientType type = attributes.getEnum("type");
		switch (type) {
		case ZkClient:
			return new String[] {ZkClientZookeeperClient.class.getName() };
		case Curator:
			return new String[] {CuratorZookeeperClient.class.getName() };
		default:
			return new String[] {ZkClientZookeeperClient.class.getName() };
		}
	}

}

/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.yijiupi.himalaya.distributedlock.annotation.EnableDistributedLock;
import com.yijiupi.himalaya.distributedlock.configuration.RedisConfiguration;
import com.yijiupi.himalaya.distributedlock.configuration.ZkLockConfiguration;
import com.yijiupi.himalaya.distributedlock.enums.DistributedLockType;

/**
 * @ClassName: LockSelector
 * @Description:
 * @author wangran
 * @date 2018年9月12日 下午2:56:03
 * 
 */
public class DistributedLockSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Class<?> annotationType = EnableDistributedLock.class;
		AnnotationAttributes attributes = AnnotationAttributes
				.fromMap(importingClassMetadata.getAnnotationAttributes(annotationType.getName(), false));
		if (attributes == null) {
			throw new IllegalArgumentException(String.format("@%s is not present on importing class '%s' as expected",
					annotationType.getSimpleName(), importingClassMetadata.getClassName()));
		}
		DistributedLockType type = attributes.getEnum("lockType");
		switch (type) {
		case Redis:
			return new String[] { RedisConfiguration.class.getName() };
		case Zookeeper:
			return new String[] { ZkLockConfiguration.class.getName() };
		default:
			return new String[] { RedisConfiguration.class.getName() };
		}
	}

}

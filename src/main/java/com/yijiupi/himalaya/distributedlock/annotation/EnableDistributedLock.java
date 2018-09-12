/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.yijiupi.himalaya.distributedlock.enums.DistributedLockType;
import com.yijiupi.himalaya.distributedlock.selector.DistributedLockSelector;

/** 
* @ClassName: EnableDistributedLock 
* @Description: 
* @author wangran
* @date 2018年9月12日 下午2:58:13 
*  
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DistributedLockSelector.class)
public @interface EnableDistributedLock {
	
	DistributedLockType lockType() default DistributedLockType.Redis;
	
}

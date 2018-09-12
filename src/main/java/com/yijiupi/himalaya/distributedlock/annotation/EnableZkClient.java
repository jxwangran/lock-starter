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

import com.yijiupi.himalaya.distributedlock.enums.ZookeeperClientType;
import com.yijiupi.himalaya.distributedlock.selector.ZkClientSelector;

/**
 * @ClassName: ZkClientConfiguration
 * @Description:
 * @author wangran
 * @date 2018年9月11日 下午7:49:28
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ZkClientSelector.class)
public @interface EnableZkClient {

	ZookeeperClientType type() default ZookeeperClientType.ZkClient;

}

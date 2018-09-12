/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.enums;

/**
 * @ClassName: ZkClientType
 * @Description:
 * @author wangran
 * @date 2018年9月11日 下午8:09:02
 * 
 */
public enum ZookeeperClientType {

	ZkClient("0"), Curator("1");

	private String type;

	private ZookeeperClientType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}

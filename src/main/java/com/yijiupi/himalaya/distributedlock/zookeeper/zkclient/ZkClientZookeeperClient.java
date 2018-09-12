/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.zookeeper.zkclient;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;

import com.yijiupi.himalaya.distributedlock.zookeeper.support.AbstractZookeeperClient;

/**
 * @ClassName: ZkClientZookeeperClient
 * @Description:
 * @author wangran
 * @date 2018年9月11日 下午7:33:31
 * 
 */
public class ZkClientZookeeperClient extends AbstractZookeeperClient {

	private final ZkClient zkClient;
	
	public ZkClientZookeeperClient(String addresses) {
		zkClient = new ZkClient(addresses);
	}

	@Override
	public void delete(String path) {
		zkClient.delete(path);
	}

	@Override
	public boolean checkExists(String path) {
		return zkClient.exists(path);
	}

	@Override
	public List<String> getChildren(String path) {
		return zkClient.getChildren(path);
	}

	@Override
	public String createEphemeralSequential(String path, String value) {
		return zkClient.createEphemeralSequential(path, value);
	}

	@Override
	protected void createPersistent(String path) {
		zkClient.createPersistent(path, true);
	}

	@Override
	protected void createEphemeral(String path) {
		zkClient.createEphemeral(path);
	}

}

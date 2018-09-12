/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.zookeeper.curator;

import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.data.Stat;

import com.yijiupi.himalaya.distributedlock.zookeeper.support.AbstractZookeeperClient;

/**
 * @ClassName: CuratorZookeeperClient
 * @Description:
 * @author wangran
 * @date 2018年9月11日 下午6:58:11
 * 
 */
public class CuratorZookeeperClient extends AbstractZookeeperClient {

	private final CuratorFramework client;
	private static final int SESSIONTIMEOUT_MS = 3000;
	private static final int CONNECTIONTIMEOUT_MS = 3000;
	private static final int SLEEPMSBETWEENRETRIES = 3000;

	public CuratorZookeeperClient(String address) {
		RetryPolicy retryPolicy = new RetryNTimes(3, SLEEPMSBETWEENRETRIES);
		client = CuratorFrameworkFactory.newClient(address, SESSIONTIMEOUT_MS, CONNECTIONTIMEOUT_MS, retryPolicy);
		client.start();
	}

	@Override
	public void delete(String path) {
		try {
			client.delete().forPath(path);
		} catch (Exception e) {
			throw new RuntimeException("删除失败");
		}
	}

	@Override
	public List<String> getChildren(String path) {
		try {
			return client.getChildren().forPath(path);
		} catch (NoNodeException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException("获取子节点失败");
		}
	}

	@Override
	public String createEphemeralSequential(String path, String value) {
		try {
			String sequence = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
					.forPath(path, value.getBytes());
			return sequence;
		} catch (Exception e) {
			throw new RuntimeException("创建持久化节点失败");
		}
	}

	@Override
	protected void createPersistent(String path) {
		try {
			client.create().forPath(path);
		} catch (NodeExistsException e) {
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	protected void createEphemeral(String path) {
		try {
			client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
		} catch (NodeExistsException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkExists(String path) {
		try {
			Stat state = client.checkExists().forPath(path);
			if (state == null) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}

/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.zookeeper.lock;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yijiupi.himalaya.distributedlock.zookeeper.ZookeeperClient;

/**
 * @ClassName: ZookeeperDistributedLock
 * @Description:
 * @author wangran
 * @date 2018年9月11日 下午3:21:27
 * 
 */
@Component
public class ZookeeperDistributedLock extends AbstractZookeeperDistributedLock {

	@Autowired
	private ZookeeperClient zkClient;
	private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperDistributedLock.class);
	private static final String ROOT_LOCK_DIR = "/supplyLock";
	private static final String LINE_SIGN = "-";

	@Override
	public boolean lock(String key, String value, Long expire) {
		try {
			boolean exists = zkClient.checkExists(ROOT_LOCK_DIR);
			if (!exists) {
				zkClient.create(ROOT_LOCK_DIR, true);
			}
			String subDir = ROOT_LOCK_DIR.concat("/").concat(key).concat(LINE_SIGN);
			String sequence = zkClient.createEphemeralSequential(subDir, value);
			System.err.println(sequence);
			List<String> sequenceList = zkClient.getChildren(ROOT_LOCK_DIR);
			List<Long> seqLongList = sequenceList.stream().map(model -> {
				String sub = model.substring(model.lastIndexOf(LINE_SIGN) + 1, model.length());
				return Long.valueOf(sub);
			}).sorted().collect(Collectors.toList());
			Long currentSeq = Long.valueOf(sequence.substring(sequence.lastIndexOf(LINE_SIGN) + 1, sequence.length()));
			Long firstSequence = seqLongList.stream().findFirst().get();
			if (currentSeq.equals(firstSequence)) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Zookeeper加锁失败 key : " + key, e);
			throw e;
		}
		return false;
	}

	@Override
	public boolean releaseLock(String key) {
		try {
			zkClient.delete(key);
			return true;
		} catch (Exception e) {
			LOGGER.error("Zookeeper解锁失败 key : " + key, e);
			throw e;
		}
	}

}

/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya.distributedlock.zookeeper;

import java.util.List;

/** 
* @ClassName: ZookeeperClient 
* @Description: 
* @author wangran
* @date 2018年9月11日 下午5:52:28 
*  
*/
public interface ZookeeperClient {
	
	void create(String path, boolean ephemeral);
	
	void delete(String path);
	
	boolean checkExists(String path);
	
	List<String> getChildren(String path);
	
	String createEphemeralSequential(String path, String value);
	
}

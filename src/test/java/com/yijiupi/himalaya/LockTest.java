/**   
 * Copyright © 2017 北京易酒批电子商务有限公司. All rights reserved.
 */
package com.yijiupi.himalaya;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** 
* @ClassName: LockTest 
* @Description: 
* @author wangran
* @date 2018年9月12日 下午2:10:37 
*  
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DistributedLockApp.class)
public class LockTest {

	
	@Test
	public void lockTest() {
		
	}
	
}

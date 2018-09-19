package com.yijiupi.himalaya;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yijiupi.himalaya.distributedlock.annotation.EnableDistributedLock;
import com.yijiupi.himalaya.distributedlock.enums.DistributedLockType;

@SpringBootApplication
@EnableDistributedLock(lockType = DistributedLockType.Zookeeper)
public class DistributedLockApp {
	public static void main(String[] args) throws InterruptedException {
		SpringApplication app = new SpringApplication(DistributedLockApp.class);
		// 不启动WEB 环境
		app.setWebEnvironment(false);
		app.run(args);
		CountDownLatch latch = new CountDownLatch(1);
		latch.await();
	}
}

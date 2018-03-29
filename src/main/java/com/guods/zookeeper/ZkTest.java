package com.guods.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZkTest {

	// 定义session失效时间
	private static final int SESSION_TIME = 5000;
	// zookeeper服务器地址
	private static final String ZK_ADDRESS = "192.168.80.101:2181,192.168.80.101:2182,192.168.80.101:2183";

	public static void main(String[] args) throws Exception {
		
		ZkClient zkClient = new ZkClient(SESSION_TIME, ZK_ADDRESS).createConnection();
		zkClient.createNode("/Pnode", "hello", Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zkClient.createNode("/Pnode/Cnode", "192.168.1.6", Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.in.read();
		zkClient.close();
	}
}

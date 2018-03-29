package com.guods.zookeeper;

public class ZkTest2 {

	// 定义session失效时间
	private static final int SESSION_TIME = 5000;
	// zookeeper服务器地址
	private static final String ZK_ADDRESS = "192.168.80.101:2181,192.168.80.101:2182,192.168.80.101:2183";

	public static void main(String[] args) throws Exception {
		
		ZkClient zkClient = new ZkClient(SESSION_TIME, ZK_ADDRESS).createConnection();
//		List<String> children = zkClient.getChildren("/Pnode", true);
//		System.out.println(children);
		System.in.read();
		zkClient.close();
	}
}

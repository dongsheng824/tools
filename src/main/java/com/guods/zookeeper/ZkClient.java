package com.guods.zookeeper;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkClient implements Watcher {

	private static final Logger logger = LoggerFactory.getLogger(ZkClient.class);
	// 定义session失效时间
	private int sessionTime;
	// zookeeper服务器地址
	private String zkAddress;
	AtomicInteger seq = new AtomicInteger();
	// 信号量设置，用于等待zookeeper连接建立之后，通知阻塞程序继续向下执行
	private CountDownLatch connectedSemaphore = new CountDownLatch(1);

	private ZooKeeper zooKeeper = null;

	public ZkClient(int sessionTime, String zkAddress) {
		super();
		this.sessionTime = sessionTime;
		this.zkAddress = zkAddress;
	}

	/**
	 * 判断znode节点是否存在
	 * 
	 * @param path
	 *            节点路径
	 * @param watch
	 *            是否监控这个目录节点，这里的 watcher是在创建ZooKeeper实例时指定的watcher
	 * @return
	 */
	public Stat exists(String path, boolean watch) {
		try {
			return zooKeeper.exists(path, watch);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("判断znode节点是否存在发生异常:{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 创建节点
	 * 
	 * @param path
	 *            节点路径
	 * @param data
	 *            数据内容
	 * @param acl
	 *            访问控制列表
	 * @param createMode
	 *            znode创建类型
	 * @return
	 */
	public boolean createNode(String path, String data, List<ACL> acl, CreateMode createMode) {
		try {
			// 设置监控(由于zookeeper的监控都是一次性的，所以每次必须设置监控)
			exists(path, true);
			String resultPath = zooKeeper.create(path, data.getBytes(), acl, createMode);
			logger.info(String.format("节点创建成功，path: %s", resultPath));
		} catch (Exception e) {
			logger.error("节点创建失败:{}", e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 更新指定节点数据内容
	 * 
	 * @param path
	 *            节点路径
	 * @param data
	 *            数据内容
	 * @return
	 */
	public boolean updateNode(String path, String data) {
		try {
			Stat stat = zooKeeper.setData(path, data.getBytes(), -1);
			logger.info("更新节点数据成功，path：" + path + ", stat: " + stat);
		} catch (Exception e) {
			logger.error("更新节点数据失败:{}", e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 删除指定节点
	 * 
	 * @param path
	 * @return
	 */
	public boolean deleteNode(String path) {
		try {
			zooKeeper.delete(path, -1);
			logger.info("删除节点成功，path：" + path);
		} catch (Exception e) {
			logger.error("删除节点失败:{}", e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 读取节点数据
	 * 
	 * @param path
	 *            节点路径
	 * @param watch
	 *            是否监控这个目录节点，这里的 watcher是在创建ZooKeeper实例时指定的watcher
	 * @return
	 */
	public String getNodeData(String path, boolean watch) {
		try {
			Stat stat = exists(path, watch);
			if (stat != null) {
				return new String(zooKeeper.getData(path, watch, stat));
			}
		} catch (Exception e) {
			logger.error("读取节点数据内容失败:{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 获取子节点
	 * 
	 * @param path
	 *            节点路径
	 * @param watch
	 *            是否监控这个目录节点，这里的 watcher是在创建ZooKeeper实例时指定的watcher
	 * @return
	 */
	public List<String> getChildren(String path, boolean watch) {
		try {
			List<String> childrenList = zooKeeper.getChildren(path, watch);
			//打印节点数据
			logger.info("子节点列表：" + childrenList);
			for (String child : childrenList) {
				String data = getNodeData(path + "/" + child, false);
				logger.info("子节点数据：" + data);
			}
			return childrenList;
		} catch (Exception e) {
			logger.error("获取子节点失败:{}", e.getMessage());
			//取不到子节点的情况下，查询该节点，继续添加监控
			getNodeData(path, watch);
		}
		return null;
	}

	/**
	 * 创建ZK连接
	 * 
	 * @param connectAddr
	 * @param sessionTimeout
	 */
	public ZkClient createConnection() {
		this.close();
		try {
			zooKeeper = new ZooKeeper(zkAddress, sessionTime, this);
			logger.info("开始连接ZK服务器...");
			// zk连接未创建成功进行阻塞
			connectedSemaphore.await();
		} catch (Exception e) {
			logger.error("ZK连接创建失败:{}", e.getMessage());
		}
		return this;
	}

	/**
	 * 关闭ZK连接
	 */
	public void close() {
		if (zooKeeper != null) {
			try {
				zooKeeper.close();
				logger.info("ZK连接关闭成功");
			} catch (InterruptedException e) {
				logger.error("ZK连接关闭失败:{}", e.getMessage());
			}
		}
	}

	@Override
	public void process(WatchedEvent event) {
		logger.info("进入process()方法...event = " + event);

		if (event == null) {
			return;
		}

		KeeperState keeperState = event.getState(); // 连接状态
		EventType eventType = event.getType(); // 事件类型
		String path = event.getPath(); // 受影响的path

		String logPrefix = "【Watcher-" + this.seq.incrementAndGet() + "】";
		logger.info(String.format("%s收到Watcher通知...", logPrefix));
		logger.info(String.format("%s连接状态：%s", logPrefix, keeperState));
		logger.info(String.format("%s事件类型：%s", logPrefix, eventType));
		logger.info(String.format("%s受影响的path：%s", logPrefix, path));

		if (KeeperState.SyncConnected == keeperState) {
			if (EventType.None == eventType) {
				// 成功连接上ZK服务器
				logger.info(logPrefix + "成功连接上ZK服务器...");
				connectedSemaphore.countDown();

			} else if (EventType.NodeCreated == eventType) {
				// 创建节点
				logger.info(logPrefix + "节点创建");
				this.exists(path, true);

			} else if (EventType.NodeDataChanged == eventType) {
				// 更新节点
				logger.info(logPrefix + "节点数据更新");
				logger.info(logPrefix + "数据内容: " + this.getNodeData(path, true));
			} else if (EventType.NodeChildrenChanged == eventType) {
				// 更新子节点
				logger.info(logPrefix + "子节点变更");
				List<String> childrenList = this.getChildren(path, true);
//				logger.info(logPrefix + "子节点列表：" + childrenList);
//				for (String child : childrenList) {
//					String data = getNodeData(path + "/" + child, false);
//					logger.info(logPrefix + "子节点数据：" + data);
//				}
				
			} else if (EventType.NodeDeleted == eventType) {
				// 删除节点
				logger.info(logPrefix + "节点 " + path + " 被删除");
				logger.info(logPrefix + "数据内容: " + this.getNodeData(path, true));
			}

		} else if (KeeperState.Disconnected == keeperState) {
			logger.info(logPrefix + "与ZK服务器断开连接");
		} else if (KeeperState.AuthFailed == keeperState) {
			logger.info(logPrefix + "权限检查失败");
		} else if (KeeperState.Expired == keeperState) {
			logger.info(logPrefix + "会话失效");
		}
	}
}

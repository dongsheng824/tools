package com.guods.lockbyredis;

/**
 * 锁实体
 * @author guods
 *
 */
public class LockEntity {

	private String lockKey;
	private String requestId;
	private int state = 1;
	private long expireTime;
	
	public LockEntity() {
		super();
	}

	public LockEntity(String lockKey, String requestId, long expireTime) {
		super();
		this.lockKey = lockKey;
		this.requestId = requestId;
		this.expireTime = expireTime;
	}

	/**
	 * 把redis获取的锁转换成锁实体
	 * @param lockKey
	 * @param lockValue
	 * @return
	 * @throws Exception
	 */
	public static LockEntity buildByKV(String lockKey, String lockValue) throws Exception {
		LockEntity lockEntity = new LockEntity();
		lockEntity.setLockKey(lockKey);
		if (lockValue != null) {
			try {
				String[] split = lockValue.split(":");
				lockEntity.setRequestId(split[0]);
				lockEntity.setState(Integer.valueOf(split[1]));
			} catch (Exception e) {
				throw new Exception("value值格式转换错误！正确格式requestId:state");
			}
		}
		return lockEntity;
	}
	
	public LockEntity lockIncrease() {
		this.state = this.state + 1;
		return this;
	}
	
	public LockEntity lockDecrease() {
		this.state = this.state - 1;
		if (this.state < 1) {
			this.state = 0;
		}
		return this;
	}
	
	public String getLockValue() {
		return requestId + ":" + state;
	}
	
	public String getLockKey() {
		return lockKey;
	}

	public void setLockKey(String lockKey) {
		this.lockKey = lockKey;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

}

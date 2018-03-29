package com.guods.proxy;

import java.util.Comparator;

/**
 * 根据Notify的order给Notify排序
 * @author guods
 *
 */
public class NotifyComparator implements Comparator<Notify> {

	@Override
	public int compare(Notify o1, Notify o2) {
		return o1.getOrder() - o2.getOrder();
	}

}

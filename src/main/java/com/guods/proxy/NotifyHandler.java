package com.guods.proxy;

import java.util.List;

public class NotifyHandler {

	public Object handleBeforeMethods(List<Notify> notifyList, Object target) {
		Notify notify;
		Object result = null;
		for (int i = 0; i < notifyList.size(); i++) {
			notify = notifyList.get(i);
			result = notify.before(target);
		}
		return result;
	}

	public Object handleAfterMethods(List<Notify> notifyList, Object target) {
		Notify notify;
		Object result = null;
		for (int i = notifyList.size() - 1; i >= 0; i--) {
			notify = notifyList.get(i);
			result = notify.after(target);
		}
		return result;
	}

	public Object handleExceptionMethods(List<Notify> notifyList, Object target) throws Exception{
		Notify notify;
		Object result = null;
		for (int i = notifyList.size() - 1; i >= 0; i--) {
			notify = notifyList.get(i);
			result = notify.exception(target);
		}
		return result;
	}

	public Object handleFinalMethods(List<Notify> notifyList, Object target) {
		Notify notify;
		Object result = null;
		for (int i = notifyList.size() - 1; i >= 0; i--) {
			notify = notifyList.get(i);
			result = notify.finallyNotify(target);
		}
		return result;
	}
}

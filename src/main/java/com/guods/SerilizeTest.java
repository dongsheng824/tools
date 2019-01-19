package com.guods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import com.guods.tools.FastJsonUtil;
import com.guods.tools.model.User;

public class SerilizeTest {

	public static void main(String[] args) throws Exception {
		long time0, time1;
		ArrayList<String> addr = new ArrayList<String>();
		addr.add("浙江省杭州市下城区");
		addr.add("江苏省南京市鼓楼区");
		ArrayList<User> userList = new ArrayList<User>();
		for (long i = 0; i < 10000; i++) {
			userList.add(new User(i, "测试" + i, addr));
		}
		////////////////////序列化
		time0 = System.currentTimeMillis();
//		List<String> jsonList = obj2StrByJson(userList);
		time1 = System.currentTimeMillis();
		System.out.println("Json序列化时间: " + (time1 - time0));
//		System.out.println(jsonList);
		//
		time0 = System.currentTimeMillis();
//		List<byte[]> serizeList = obj2StrBySerize(userList);
		time1 = System.currentTimeMillis();
		System.out.println("Serize序列化时间: " + (time1 - time0));
//		for (byte[] bs : serizeList) {
//			System.out.println(new String(bs));
//		}
		///////////////////反序列化
		time0 = System.currentTimeMillis();
//		List<User> jsonUserList = str2ObjByJson(jsonList);
		time1 = System.currentTimeMillis();
		System.out.println("Json反序列化时间: " + (time1 - time0));
//		System.out.println(jsonUserList);
		time0 = System.currentTimeMillis();
//		List<User> serizeUserList = str2ObjBySerize(serizeList);
		time1 = System.currentTimeMillis();
		System.out.println("Serize反序列化时间: " + (time1 - time0));
//		for (User u : serizeUserList) {
//			System.out.println(u);
//		}
	}

	public static List<String> obj2StrByJson(List<User> objList) {
		ArrayList<String> stringList = new ArrayList<String>();
		for (User user : objList) {
			stringList.add(FastJsonUtil.object2String(user));
		}
		return stringList;
	}

	public static List<byte[]> obj2StrBySerize(List<User> objList) throws Exception {
		ArrayList<byte[]> bytesList = new ArrayList<byte[]>();
		for (User User : objList) {
			bytesList.add(SerializationUtils.serialize((Serializable) User));
		}
		return bytesList;
	}

	public static List<User> str2ObjByJson(List<String> strList) {
		ArrayList<User> stringList = new ArrayList<User>();
		for (String string : strList) {
			stringList.add(FastJsonUtil.parseJson(string, User.class));
		}
		return stringList;
	}

	public static List<User> str2ObjBySerize(List<byte[]> bytesList) throws Exception {
		ArrayList<User> userList = new ArrayList<User>();
		for (byte[] bytes : bytesList) {
			userList.add(SerializationUtils.deserialize(bytes));
		}
		return userList;
	}
}

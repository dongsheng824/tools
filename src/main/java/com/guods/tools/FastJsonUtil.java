package com.guods.tools;

import com.alibaba.fastjson.JSON;

public class FastJsonUtil {

	public static String object2String(Object object){
		return JSON.toJSONString(object);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T parseJson(String json, Class<T> clazz){
		Object object = JSON.parseObject(json, clazz);
		return (T)object;
	}
}

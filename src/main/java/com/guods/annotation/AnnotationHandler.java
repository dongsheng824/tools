package com.guods.annotation;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AnnotationHandler {

	public static <T> T handle(T entity) throws IllegalArgumentException, IllegalAccessException, ParseException {
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		String type;
		for (Field field : fields) {
			type = field.getType().getSimpleName();
			field.setAccessible(true);
			if ("string".equalsIgnoreCase(type)) {
				StringSetter stringSetter;
				stringSetter = field.getAnnotation(StringSetter.class);
				if (stringSetter != null) {
					field.set(entity, stringSetter.value());
				}
			}else if ("date".equalsIgnoreCase(type)) {
				DateFormate dateFormat = field.getAnnotation(DateFormate.class);
				if (dateFormat != null) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat.value());
					field.set(entity, simpleDateFormat.parse("2017-12-12"));
				}
			} 
		}
		return entity;
	}
}

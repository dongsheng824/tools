package com.guods.time;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeDemo {

	public static void main(String[] args) {
		LocalDate date = LocalDate.now();
		System.out.println(date);
		LocalTime time = LocalTime.now();
		System.out.println(time);
		LocalDateTime dateTime = LocalDateTime.now();
		System.out.println(dateTime);
		LocalDateTime dateTime2 = LocalDateTime.now();
		System.out.println(dateTime2);
		System.out.println(Duration.between(dateTime, dateTime2));
	}
}

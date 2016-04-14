package com.prize.util;

import java.util.UUID;

public class UUIDUtil {
	public static String getUUIDString(){
		StringBuilder sb = new StringBuilder();
		for (String s : UUID.randomUUID().toString().split("-")) {
			sb.append(s);
		}
		return sb.toString();
	}
}

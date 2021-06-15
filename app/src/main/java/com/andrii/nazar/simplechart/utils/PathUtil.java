package com.andrii.nazar.simplechart.utils;

public class PathUtil {

	public static String buildPath(String[] pathParts) {
		String result;
		
		result = "";
		
		for (String part : pathParts) {
			result += part + "/";
		}
		
		if (!result.equals("")) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}
}

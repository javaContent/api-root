package com.wd.cloud.searchserver.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseUtil {
	private static final Pattern P = Pattern.compile("[\u4e00-\u9fa5]");
	public static boolean isChinese(String str) {
		Matcher m = P.matcher(str);
		return m.find();
	}
}

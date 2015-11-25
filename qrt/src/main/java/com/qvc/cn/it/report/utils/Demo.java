package com.qvc.cn.it.report.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("\\[([^\\[]*)\\]");
		Matcher match = pattern.matcher("[ACTUAL_RESULT] + [AFTER_CHANGE] + [BEFORE_CHANGE] + [DEFECT_STATUS]");
		while (match.find()) {
			System.out.println(match.group(1));
		}
	}

}

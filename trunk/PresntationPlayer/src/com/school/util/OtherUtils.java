package com.school.util;

public class OtherUtils {
	public static final String PDF_FILE_EXTENSION = "pdf";
	public static  String concatStringsAscending(String... str){
		StringBuilder builder=new StringBuilder();
		for (String string : str) {
			builder.append(string);
		}
		return builder.toString();
	}
	
}

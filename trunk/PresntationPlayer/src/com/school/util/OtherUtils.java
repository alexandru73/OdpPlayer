package com.school.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OtherUtils {
	public static final String PDF_FILE_EXTENSION = "pdf";

	public static String concatStringsAscending(String... str) {
		StringBuilder builder = new StringBuilder();
		for (String string : str) {
			builder.append(string);
		}
		return builder.toString();
	}

	public static enum UserRole {
		ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER");

		private String scope;

		private UserRole(String scope) {
			this.scope = scope;
		}

		public String getRole() {
			return scope;
		}
	}

	public static String hashPassword(String password) {
		String hashword = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes());
			BigInteger hash = new BigInteger(1, md5.digest());
			hashword = hash.toString(16);
		} catch (NoSuchAlgorithmException nsae) {
			// ignore
		}
		return pad(hashword, 32, '0');
	}

	private static String pad(String s, int length, char pad) {
		StringBuffer buffer = new StringBuffer(s);
		while (buffer.length() < length) {
			buffer.insert(0, pad);
		}
		return buffer.toString();
	}

}

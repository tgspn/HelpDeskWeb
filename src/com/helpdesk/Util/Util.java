package com.helpdesk.Util;

import java.security.NoSuchAlgorithmException;

public class Util {
	public static String MD5(String md5) throws NoSuchAlgorithmException {

		java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		byte[] array = md.digest(md5.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();

	}
	
	
}

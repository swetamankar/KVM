package com.apigee.edge.config.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Crypto {
	
	public static final int SALT_LEN = 8;
	public static final int IV_LENGTH = 16;
	public static final int PBKDF2_ITERATIONS = 1024;
	public static final int HASH_BYTES = 128;

	private static byte[] getSaltBytes() throws Exception {
		return new byte [SALT_LEN];
	}

	private static char[] getMasterPassword(String password) {
	    return password.toCharArray();
	}

	public static String encrpytString (String password, String input) throws Exception {
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	    PBEKeySpec spec = new PBEKeySpec(getMasterPassword(password), getSaltBytes(), PBKDF2_ITERATIONS, HASH_BYTES);
	    SecretKey secretKey = factory.generateSecret(spec);
	    SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, secret);
	    byte[] ivBytes = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
	    byte[] encryptedTextBytes = cipher.doFinal(input.getBytes("UTF-8"));
	    byte[] finalByteArray = new byte[ivBytes.length + encryptedTextBytes.length]; 
	    System.arraycopy(ivBytes, 0, finalByteArray, 0, ivBytes.length);
	    System.arraycopy(encryptedTextBytes, 0, finalByteArray, ivBytes.length, encryptedTextBytes.length);
	    return DatatypeConverter.printBase64Binary(finalByteArray);        
	}
	
	public static String decryptString (String password, String input) throws Exception {
	    if (input.length() <= IV_LENGTH) {
	        throw new Exception("The input string is not long enough to contain the initialisation bytes and data.");
	    }
	    byte[] byteArray = DatatypeConverter.parseBase64Binary(input);
	    byte[] ivBytes = new byte[IV_LENGTH];
	    System.arraycopy(byteArray, 0, ivBytes, 0, 16);
	    byte[] encryptedTextBytes = new byte[byteArray.length - ivBytes.length];
	    System.arraycopy(byteArray, IV_LENGTH, encryptedTextBytes, 0, encryptedTextBytes.length);
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	    PBEKeySpec spec = new PBEKeySpec(getMasterPassword(password), getSaltBytes(), PBKDF2_ITERATIONS, HASH_BYTES);
	    SecretKey secretKey = factory.generateSecret(spec);
	    SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
	    byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
	    return new String(decryptedTextBytes);
	}

	public static void main(String[] args) throws Exception {
		String password = "pwd";
		//System.out.println(encrpytString(password, "Phunes2mukuKebac"));
		System.out.println(decryptString(password, "tPmKusaLJYtI8ENmb/+SXhkXfOJkE2vjtPyvl3wX57PdFdOL53FJ/VUAJBb4vAn6"));
	}
}
//"consumerKey":"ctqZf7sx4sjYQ6WSmq4m34vT9Xbss8wFBz5Fq+Z9h49//1PPir7lODKI528u2rJpMYVuMvoVnh/MURDpDpblOg==",
//"consumerSecret":"tPmKusaLJYtI8ENmb/+SXhkXfOJkE2vjtPyvl3wX57PdFdOL53FJ/VUAJBb4vAn6"



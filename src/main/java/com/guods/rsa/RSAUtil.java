package com.guods.rsa;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSAUtil {

	// RSA最大加密明文大小
	private static final int MAX_ENCRYPT_BLOCK = 117;

	// RSA最大解密密文大小
	private static final int MAX_DECRYPT_BLOCK = 128;

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	/**
	 * 生成密钥对
	 * @param keyLength
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyPair genKeyPair(int keyLength) throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		return keyPairGenerator.generateKeyPair();
	}

	/**
	 * 公钥加密
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 分段加密
		int inputLen = content.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(content, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(content, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 私钥加密
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		// 分段加密
		int inputLen = content.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(content, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(content, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 公钥加密字符串
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encrypt2Str(String data, PublicKey publicKey) throws Exception {
		byte[] encrypt = encrypt(data.getBytes(), publicKey);
		return Base64.getEncoder().encodeToString(encrypt);
	}

	/**
	 * 私钥加密字符串
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String encrypt2Str(String data, PrivateKey privateKey) throws Exception {
		byte[] encrypt = encrypt(data.getBytes(), privateKey);
		return Base64.getEncoder().encodeToString(encrypt);
	}

	/**
	 * 公钥解密
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		int inputLen = content.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(content, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(content, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 私钥解密
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		int inputLen = content.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(content, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(content, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 私钥解密字符串
	 * 
	 * @param secData
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt2Str(String secData, PrivateKey privateKey) throws Exception {
		byte[] decodeContent = Base64.getDecoder().decode(secData);
		byte[] decrypt = decrypt(decodeContent, privateKey);
		return new String(decrypt);
	}

	/**
	 * 公钥解密
	 * 
	 * @param secData
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt2Str(String secData, PublicKey publicKey) throws Exception {
		byte[] decodeContent = Base64.getDecoder().decode(secData);
		byte[] decrypt = decrypt(decodeContent, publicKey);
		return new String(decrypt);
	}

	/**
	 * 将base64编码后的公钥字符串转成PublicKey实例
	 * @param publicKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes());
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}

	/**
	 * 将base64编码后的私钥字符串转成PrivateKey实例
	 * @param privateKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

}

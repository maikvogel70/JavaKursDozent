package util;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Crypto
{

	public static final String	 AES	 = "AES";
	// Der Schlüssel muss 16 Bytes lang sein.
	private static final String	secretKey	= "<schluesselwort>";

	public static String encrypt(String value) throws GeneralSecurityException
	{

		SecretKeySpec sks = getSecretKeySpec(secretKey);
		Cipher cipher = Cipher.getInstance(Crypto.AES);
		cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
		byte[] encrypted = cipher.doFinal(value.getBytes());
		return byteArrayToHexString(encrypted);
	}

	public static String decrypt(String value) throws GeneralSecurityException
	{
		SecretKeySpec sks = getSecretKeySpec(secretKey);
		Cipher cipher = Cipher.getInstance(Crypto.AES);
		cipher.init(Cipher.DECRYPT_MODE, sks);
		byte[] decrypted = cipher.doFinal(hexStringToByteArray(value));
		return new String(decrypted);
	}

	private static SecretKeySpec getSecretKeySpec(String secretKey) throws NoSuchAlgorithmException
	{
		byte[] key = secretKey.getBytes();
		SecretKeySpec sks = new SecretKeySpec(key, Crypto.AES);
		return sks;
	}

	private static String byteArrayToHexString(byte[] b)
	{
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++)
		{
			int v = b[i] & 0xff;
			if (v < 16)
			{
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

	private static byte[] hexStringToByteArray(String s)
	{
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++)
		{
			int index = i * 2;
			int v = Integer.parseInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}

}

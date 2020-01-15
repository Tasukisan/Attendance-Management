package struct;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Matthew Schwartz This class provides a basic SHA-512 hash of the
 *         input String. NOTE: SHA-512 is insufficient for actual password
 *         encryption.
 */
public class Encrypt
{
	public static String hash(String in)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] bit = md.digest(in.getBytes());
			BigInteger bi = new BigInteger(1, bit);
			String hash = bi.toString(16);
			while (hash.length() < 32)
			{
				hash = "0" + hash;
			}
			return hash;
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println(e.toString());
			return null;
		}
	}
}

package com.hooni.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtil
{
	public static String sha1(String str)
	{
	    return toHexString(sha1(str.getBytes()));
	}

	
	public static byte[] sha1(byte []bytes)
	{
	    MessageDigest sha = _sha1Digest.get();
	    sha.update(bytes);
	    return sha.digest();
	}

	public static byte[] md5(byte[] bytes)
	{
        MessageDigest md = _md5Digest.get();
        md.update(bytes, 0, bytes.length);
        return md.digest();
	}

	public static String md5(String s)
	{
	    return toHexString(md5(s.getBytes()));
	}

	public static String toHexString(byte [] bytes, int offset, int len)
    {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < len; i++)
        {
            buf.append(HEX[bytes[i+offset] >>> 4 & 0x0F]);
            buf.append(HEX[bytes[i+offset] & 0x0F]);
        }
        return buf.toString();
    }
    
    public static String toHexString(byte[] bytes)
    {
        return toHexString(bytes, 0, bytes.length);
    }
    
	private static final ThreadLocal<MessageDigest> _md5Digest =
		new ThreadLocal<MessageDigest>()
		{
			@Override
			protected MessageDigest initialValue()
			{
			    try { return MessageDigest.getInstance("MD5"); }
			    catch(NoSuchAlgorithmException e) { throw new RuntimeException(e); }
			}
		};


	private static final ThreadLocal<MessageDigest> _sha1Digest =
		new ThreadLocal<MessageDigest>()
		{
			@Override
			protected MessageDigest initialValue()
			{
				try { return MessageDigest.getInstance("SHA-1"); }
				catch(NoSuchAlgorithmException e) { throw new RuntimeException(e); }
			}
		};
		
	public static final char[] HEX = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}

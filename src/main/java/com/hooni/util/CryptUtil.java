// $Id$
package com.hooni.util;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.Provider;
import java.security.Security;
import java.util.Iterator;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public final class CryptUtil
{
    public static final String DESEDE_CFB8_NOPADDING = "DESede/CFB8/NoPadding";

    public static String encrypt(String ctext)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, DES_KEY);
            byte[] etext = cipher.doFinal(ctext.getBytes());

            return new String(Base64.encodeBase64(etext));
         }
        catch (Exception x)
        {
            throw new EncryptionException(x);
        }
    }

    public static String decrypt(String etext)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, DES_KEY);

            byte[] dtext = Base64.decodeBase64(etext.getBytes());
 
            return new String(cipher.doFinal(dtext));
        }
        catch (Exception x)
        {
            throw new EncryptionException(x);
        }
    }

    public static byte[] encryptDesEde(String source, String key, String initVector)
    {
        try
        {
            SecretKey secret = makeDesEdeKey(key);
            IvParameterSpec vec = makeInitVector(initVector); 
            Cipher cipher = Cipher.getInstance(DESEDE_CFB8_NOPADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secret, vec);
            return cipher.doFinal(source.getBytes());
        }
        catch (Exception x)
        {
            throw new EncryptionException(x);
        }
    }

    public static String decryptDesEde(byte[] source, String key, String initVector)
    {
        try
        {
            SecretKey secret = makeDesEdeKey(key);
            IvParameterSpec vec = makeInitVector(initVector); 
            Cipher cipher = Cipher.getInstance(DESEDE_CFB8_NOPADDING);
            cipher.init(Cipher.DECRYPT_MODE, secret, vec);
            return new String(cipher.doFinal(source));
        }
        catch (Exception x)
        {
            throw new EncryptionException(x);
        }
    }

    public static void showProviders()
    {
        Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++ )
        {
            System.out.println(providers[i].getName() + ": " + providers[i].getInfo());
            for (Iterator p = providers[i].keySet().iterator(); p.hasNext(); )
            {
                String key = (String)p.next();
                String value = (String)providers[i].get(key);
                System.out.println( "\t" + key + " = " + value );
            }
        }
    }

    private static SecretKey makeDesEdeKey(String key)
        throws GeneralSecurityException
    {
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
        return SecretKeyFactory.getInstance("DESede").generateSecret(spec);
    }

    private static IvParameterSpec makeInitVector(String initVector)
    {
        return new IvParameterSpec(initVector.getBytes());
    }

    private static final Key DES_KEY;
    private static final String KEY = "there's a fine line between fishing and just standing on the shore like an idiot--Steven Wright";
    private static final String CIPHER = "DES/ECB/PKCS5Padding";

    static
    {
        try
        {
            DESKeySpec pass = new DESKeySpec(KEY.getBytes()); 
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES"); 
            DES_KEY = skf.generateSecret(pass); 
        }
        catch (Exception x)
        {
            throw new EncryptionException(x);
        }
    }

	public static class EncryptionException 
        extends RuntimeException
	{
        public EncryptionException(Exception x) { super(x); }

        private static final long serialVersionUID = -8374899414180383005L;
	}

    private CryptUtil() {}
} 

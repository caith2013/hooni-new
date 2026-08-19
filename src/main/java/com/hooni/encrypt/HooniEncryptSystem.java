package com.hooni.encrypt;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import jakarta.servlet.http.Cookie;

import com.hooni.util.Token;
//import com.hooni.web.http.HooniServletRequest;
//import com.hooni.web.http.WebSession;
import com.hooni.web.util.HttpUtil;


public class HooniEncryptSystem implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/*
	public HooniEncryptSystem(WebSession webSess)
	{
		_webSess = webSess;
		
	}
	*/
	
	public HooniEncryptSystem()
	{
		// Default constructor for compatibility
	}
	
	public void setupNewKey()
	{
		_key = createKey();
		_keyCookie = HttpUtil.makeCookie(SECURITY_KEY_COOKIE, new Token(_key,0).encode());
		_keyCookie.setMaxAge(30 * 60); // 30 minutes
		_keyImage = HooniEncryptSystem.createKeyImage(_key);
	}
	
	public static BufferedImage createKeyImage(String key)
	{
		BufferedImage off_image =  null;
        int width = 100;
        int height = 25;
        off_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = off_image.createGraphics();
		g2.setPaint(Color.BLACK);
		g2.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		FontMetrics fm = g2.getFontMetrics();
		int x = off_image.getWidth()/2 - fm.stringWidth(key)/2;
        int y = off_image.getHeight() - 5;

        g2.drawRect(0, 0, off_image.getWidth()-1, off_image.getHeight()-1);
		g2.drawString(key, x, y);
		g2.dispose();
		
		return off_image;
	}
	
	/*
	public void writeKeyToCookie()
	{
		_webSess.getResponse().addCookie(_keyCookie);
	}
	public static String getKeyFromCookie(HooniServletRequest req)
	{
		String sc_cookie = req.getCookie(SECURITY_KEY_COOKIE).getValue();
		return Token.decode(sc_cookie).getPayload();
	}
	
	public String recoverKeyFromCookie()
	{
		String sc_cookie = _webSess.getRequest().getCookie(SECURITY_KEY_COOKIE).getValue();
		Token token = Token.decode(sc_cookie);
		_key = token.getPayload();
		return _key;
	}
	*/
	
	public String getKey()
	{
		return _key;
	}
	
	public String[] getEncryptedString()
	{
		return _encryptedStrs;
	}

	public String[] getDecryptedString()
	{
		return _decryptedStrs;
	}
	
	public Cookie getKeyCookie()
	{
		return _keyCookie;
	}
	
	public BufferedImage getKeyImage()
	{
		return _keyImage;
	}
	
	public static HashMap<String,String> decrypt(HashMap<String,String> userInputs, String key)
	{
		if (userInputs == null)
			throw new IllegalArgumentException("unable to encrypt or decrypt an empty collections");
		
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("unable to encrypt or decrypt with empty key");
		
		HashMap<String,String> result = new HashMap<String,String>();
		AesIO aes = new AesIO();
		
		for (String k : userInputs.keySet())
		{
			try
			{
				result.put(k, aes.decrypt(userInputs.get(k), key, 256));
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	public static HashMap<String,String> encrypt(HashMap<String,String> userInputs, String key)
	{
		if (userInputs == null)
			throw new IllegalArgumentException("unable to encrypt or decrypt an empty collections");
		
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("unable to encrypt or decrypt with empty key");
		
		HashMap<String,String> result = new HashMap<String,String>();
		AesIO aes = new AesIO();
		
		for (String k : userInputs.keySet())
		{
			try
			{
				result.put(k, aes.encrypt(userInputs.get(k), key, 256));
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void wrapInputStrings(String[] strs, boolean statue)
	{
		if (strs == null)
			throw new IllegalArgumentException("unable to encrypt or decrypt an empty of array strings");
		//if (_key == null || _key.isEmpty()) recoverKeyFromCookie();
		
		if (_key == null || _key.isEmpty())
			throw new IllegalArgumentException("unable to encrypt or decrypt with empty key");
		
		AesIO aes = new AesIO();
		
		if (statue)
		{
			_encryptedStrs = strs;
			_decryptedStrs = processDecrypt(aes,strs);
		}
		else
		{
			_decryptedStrs = strs;
			_encryptedStrs = processEncrypt(aes,strs);
		}
	}
	public static String getCookieName()
	{
		return SECURITY_KEY_COOKIE;
	}
	private String[] processDecrypt(AesIO aes, String[] strs)
	{
		String[] result = new String[strs.length];
		for (int i=0; i<strs.length; i++)
		{
			try
			{
				result[i] = aes.decrypt(strs[i], _key, 256);
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
		
	}
	
	
	private String[] processEncrypt(AesIO aes, String[] strs) 
	{
		String[] result = new String[strs.length];
		for (int i=0; i<strs.length; i++)
		{
			try
			{
				result[i] = aes.encrypt(strs[i], _key, 256);
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	private String createKey()
	{
		String result = "";
		Random rd = new Random();
		for (int i = 0;  i < 4; i++)
		{
			result = result + Integer.toHexString(rd.nextInt(128));
		}
		return result;
	}
	
	private String _key;
	private BufferedImage _keyImage;
	private transient Cookie _keyCookie;
	//private WebSession _webSess;  // Commented out - WebSession class not available
	private String[] _encryptedStrs;
	private String[] _decryptedStrs;
	private final static String SECURITY_KEY_COOKIE = "security_key_hooni.org";
}

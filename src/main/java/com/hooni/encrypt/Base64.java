package com.hooni.encrypt;

import java.nio.charset.Charset;


public class Base64
{

	public static String encode(String str)
	{
		return encode(str, false);
	}
	public static String encode(String str, boolean utf8)
	{
		Charset cset = Charset.forName("UTF-8");
		
		StringBuffer plain = new StringBuffer(str);
		
		int c = plain.length() % 3;
		String pad = "";
		if (c > 0) { while (c++ < 3) { pad += '='; plain.append('\0'); } }
		
		
		char o1,o2,o3;
		int bits,h1,h2,h3,h4;
		StringBuffer e = new StringBuffer();
		
		for (int i=0; i <plain.length(); i+=3)
		{
			o1 = plain.charAt(i);
			o2 = plain.charAt(i+1);
			o3 = plain.charAt(i+2);
			
			bits =  o1<<16 | o2<<8 | o3;
			
			h1 = bits >>18 & 0x3f;
			h2 = bits >>12 & 0x3f;
			h3 = bits >>6 & 0x3f;
			h4 = bits & 0x3f;
			
			e.append(code.charAt(h1));
			e.append(code.charAt(h2));
			e.append(code.charAt(h3));
			e.append(code.charAt(h4));
		}
		
		e = e.replace(e.length()-pad.length(), e.length(), pad);
		
		return e.toString();
	}
	
	public static String decode(String str)
	{
		return decode(str, false);
	}
	
	public static String decode(String str, boolean utf8)
	{
		String coded;
		Charset cset = Charset.forName("UTF-8");
		
		coded =  str;
		
		int h1,h2,h3,h4,bits;
		int o1,o2,o3;
		StringBuffer d = new StringBuffer();
		
		for (int c = 0;  c<coded.length(); c+=4)
		{
			h1 = code.indexOf(coded.charAt(c)+"");
			h2 = code.indexOf(coded.charAt(c+1)+"");
			h3 = code.indexOf(coded.charAt(c+2)+"");
			h4 = code.indexOf(coded.charAt(c+3)+"");
			
			bits = h1<<18 | h2<<12 | h3<<6 | h4;
			
			o1 = bits>>>16 & 0xff;
			o2 = bits>>>8 & 0xff;
			o3 = bits & 0xff;
			
			
			if (h3 == 0x40)
			{
				d.append((char)o1);
			}
			else if (h4 == 0x40)
			{
				d.append((char)o1);
				d.append((char)o2);
			}
			else
			{
				d.append((char)o1);
				d.append((char)o2);
				d.append((char)o3);
			}
			
		}
		
		
		return d.toString();
		
	}
	
	
	private static StringBuffer code= new StringBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=");
}

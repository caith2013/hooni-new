package com.hooni.encrypt;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utf8
{
	
	public static void main(String[] arg)
	{
		String str= "this is a crazy \u0080 test, you like it\u0065 or not";
		
		System.out.println(Utf8.encode(str));
	}
	public static String encode(String strUni)
	{
		StringBuffer strBuf = new StringBuffer(strUni);
		Pattern p = Pattern.compile("/[\u0080-\u07ff]/");
		Matcher m = p.matcher(strUni);
		
		int i = 0;
		while (m.find(i))
		{
			int start = m.regionStart();
			int end = m.regionEnd();
			char c = strUni.charAt(start);
			ByteArrayOutputStream input = new ByteArrayOutputStream();
			input.write(0xc0 | c>>6);
			input.write(0x80 | c&0x3f);
			strBuf.replace(start, end, "");
			strBuf.insert(start, input.toString());
			i = start = input.size();
		}
		
		Pattern p1 = Pattern.compile("/[\u0800-\uffff]/");
		m = p1.matcher(strUni);
		i = 0;
		while (m.find(i))
		{
			int start = m.regionStart();
			int end = m.regionEnd();
			char c = strUni.charAt(start);
			ByteArrayOutputStream input = new ByteArrayOutputStream();
			input.write(0xe0 | c>>12);
			input.write(0x80 | c>>6&0x3F);
			input.write(0x80 | c&0x3f);
			strBuf.replace(start, end, "");
			strBuf.insert(start, input.toString());
			i = start + input.size();
		}
		
			  return strBuf.toString();
	}

}

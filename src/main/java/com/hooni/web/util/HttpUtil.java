package com.hooni.web.util;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class HttpUtil 
{
	 public static HttpClient makeHttpClient() { return HttpClient.newHttpClient(); }
	 
	 
	    public static Map<String, Cookie> getCookies(HttpServletRequest req)
	    {
	        Cookie [] cookies = req.getCookies();
	        Map<String, Cookie> result = new HashMap<String, Cookie>();
	        
	        if ( cookies == null )
	            return result;
	        
	        for (int i=0; i < cookies.length; i++)
	            result.put(cookies[i].getName(), cookies[i]);

	        return result;
	    }

	 
	    public static Cookie makeCookie(String name, String value)
	    {
	        Cookie cookie = new Cookie(name, value);
	        cookie.setDomain("hooni.org");
	        cookie.setPath("/");
	        return cookie;
	    }
	    
	    public static void setCookieProperties(Cookie cookie, int expiry)
	    {
	    	cookie.setDomain("hooni.org");
	    	cookie.setPath("/");
	    	cookie.setMaxAge(expiry);
	    }
}

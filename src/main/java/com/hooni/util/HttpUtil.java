package com.hooni.util;

import com.hooni.Constants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil
{
	public static final String DEFAULT_URL_ENCODING = Constants.DEFAULT_ENCODING;

	public static String urlencode(String s)
	{
		return urlencode(s, DEFAULT_URL_ENCODING);
	}

	public static String urldecode(String s)
	{
		return urldecode(s, DEFAULT_URL_ENCODING);
	}

	public static String urlencode(String s, String enc)
	{
		try
		{
			return URLEncoder.encode(s, enc);
		} catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static String urldecode(String s, String enc)
	{
		try
		{
			return URLDecoder.decode(s, enc);
		} catch (UnsupportedEncodingException e)
		{
			_log.error("fail to decode url in HttpUtil", e);
			throw new RuntimeException();
		}
	}

	public static Map<String, String> parseQueryString(String query,
			String encoding)
	{
		if (StringUtils.isBlank(query))
			throw new IllegalArgumentException("Empty query!");

		String[] nvPairs = query.split("&");
		Map<String, String> result = new HashMap<String, String>();

		for (int i = 0; i < nvPairs.length; i++)
		{
			String[] nv = nvPairs[i].split("=");

			if (nv.length == 2)
			{
				String name = urldecode(nv[0], encoding);
				String val = urldecode(nv[1], encoding);

				if (result.containsKey(name))
				{
					_log.error("Duplicate " + name + " in HttpUtil");
					throw new RuntimeException();
				} else
					result.put(name, val);
			} else if (nv.length == 1)
			{
				// skip empty params
			} else
			{
				_log.error("Error in HttpUtil with query=" + query + " and i="
						+ i);
				throw new RuntimeException();
			}
		}

		return result;
	}

	/**
	 * For parsing 'POST' parameters where there is no API call to convert them
	 * to query string, but will work for 'GET' requests just the same.
	 */
	public static String paramsToQueryString(HttpServletRequest request,
			String encoding)
	{
		StringBuffer query = new StringBuffer();
		for (Enumeration params = request.getParameterNames(); params
				.hasMoreElements();)
		{
			String paramName = params.nextElement().toString();
			String[] paramVals = request.getParameterValues(paramName);
			for (int i = 0; i < paramVals.length; i++)
			{
				query.append(urlencode(paramName, encoding));
				query.append('=');
				query.append(urlencode(paramVals[i], encoding));

				if (params.hasMoreElements())
					query.append('&');
			}
		}

		return query.toString();
	}

	/** Converts HTTP headers to query string format for logging/debugging. */
	public static String headersToQueryString(HttpServletRequest request,
			String encoding)
	{
		StringBuffer headers = new StringBuffer();
		for (Enumeration headerNames = request.getHeaderNames(); headerNames
				.hasMoreElements();)
		{
			String headerName = headerNames.nextElement().toString();
			String headerVal = request.getHeader(headerName);

			headers.append(urlencode(headerName, encoding));
			headers.append('=');
			headers.append(urlencode(headerVal, encoding));

			if (headerNames.hasMoreElements())
				headers.append('&');
		}

		return headers.toString();
	}

	/**
	 * HTTP post encoded parameters to a given url.
	 * 
	 * NOT STATIC for unit tests, @see MockHttpUtil
	 */
	public BufferedReader postForm(String url, String urlEncodedParams)
			throws IOException
	{
		URLConnection conn = (new URL(url)).openConnection();
		conn.setDoOutput(true);
		conn.setRequestProperty(HttpUtil.HTTP_HEADER_CONTENT_TYPE,
				HttpUtil.CONTENT_TYPE_FORM_URLENCODED);
		PrintWriter writer = new PrintWriter(conn.getOutputStream());
		writer.println(urlEncodedParams);
		writer.close();

		return new BufferedReader(new InputStreamReader(conn.getInputStream()));
	}

	/** Returns a Map (String {cookie name} -to- Cookie). */
	public static Map<String, Cookie> getCookies(HttpServletRequest req)
	{
		Cookie[] cookies = req.getCookies();
		Map<String, Cookie> result = new HashMap<String, Cookie>();

		if (cookies == null)
			return result;

		for (int i = 0; i < cookies.length; i++)
			result.put(cookies[i].getName(), cookies[i]);

		return result;
	}

	// just create base cookie
	public static Cookie makeCookie(String name, String value)
	{
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain("localhost");
		cookie.setPath("/");
		return cookie;
	}

	private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
	private static final String CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";

	private static final Logger _log = LoggerFactory.getLogger(HttpUtil.class);
}

package com.hooni.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

public class Url implements Cloneable
{

	public Url(String base)
	{
		this(base, "");
	}

	public Url(String base, String queryString)
	{
		_base = base;
		_queryString = StringUtils.defaultString(queryString);
		_parameterMap = null;
	}

	public String getBase()
	{
		return _base;
	}

	public boolean hasParameter(String key)
	{
		return getParameterMap().get(key) != null;
	}

	public String getParameter(String key)
	{
		return getParameterMap().get(key).toString();
	}

	public Url setParameter(String key, Object value)
	{
		if (value == null)
			getParameterMap().remove(key);
		else
			getParameterMap().put(key, value);
		_queryString = null;
		return this;
	}

	@Override
	public String toString()
	{
		String qString = getQueryString();
		if (qString.isEmpty())
			return _base;
		else
			return _base + '?' + qString;
	}

	@Override
	public boolean equals(Object o)
	{
		return (o != null) && toString().equals(o.toString());
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object clone()
	{
		// the only reason to clone this is to set different parameters without
		// changing the original object
		// so let's clone it from parameter map, not from query string
		return new Url(_base, (HashMap<String, Object>) getParameterMap()
				.clone());
	}

	private Url(String base, HashMap<String, Object> parameterMap)
	{
		_base = base;
		_queryString = null;
		_parameterMap = parameterMap;
	}

	/**
	 * traverses parameter map and formats query string (lazily cached)
	 * 
	 * @return query string
	 */
	private String getQueryString()
	{
		if (_queryString == null)
		{
			StringBuffer sb = new StringBuffer(_parameterMap.size() * 20);

			Iterator<Entry<String, Object>> paramIter = _parameterMap
					.entrySet().iterator();
			while (paramIter.hasNext())
			{
				Entry<String, Object> param = paramIter.next();

				// stupid little optimization, a lot of our params are numbers
				// and don't need encoding
				if (param.getValue() instanceof Number)
					sb.append(param.getKey()).append('=')
							.append(param.getValue().toString());
				else
					sb.append(param.getKey())
							.append('=')
							.append(HttpUtil.urlencode(param.getValue()
									.toString()));

				if (paramIter.hasNext())
					sb.append('&');
			}

			_queryString = sb.toString();
		}

		return _queryString;
	}

	/**
	 * parses query string and builds parameter map (lazily cached)
	 * 
	 * @return parameter map
	 */
	private HashMap<String, Object> getParameterMap()
	{
		if (_parameterMap == null)
		{
			_parameterMap = new HashMap<String, Object>();
			for (String param : StringUtils.split(_queryString, '&'))
			{
				int indexOfEquals = param.indexOf('=');
				if (indexOfEquals < 0) // no equals sign
					_parameterMap.put(param, ""); // put empty value
				else
					_parameterMap.put(param.substring(0, indexOfEquals),
							HttpUtil.urldecode(param
									.substring(indexOfEquals + 1)));
			}
		}

		return _parameterMap;
	}

	private String _base, _queryString;
	private HashMap<String, Object> _parameterMap;

}

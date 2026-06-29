package com.hooni.util;


public class XfileEntry
{
	 public XfileEntry(String key, String val)
	    {
	        _key = key;
	        _val = val;
	    }

	    public String getKey()
	    {
	        return _key;
	    }
	    public void setKey(String key)
	    {
	        _key = key;
	    }
	    public String getVal()
	    {
	        return _val;
	    }
	    public void setVal(String val)
	    {
	        _val = val;
	    }
	    
	    
	    private String _key;
	    private String _val;
}

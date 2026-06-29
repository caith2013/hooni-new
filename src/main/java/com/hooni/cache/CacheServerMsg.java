package com.hooni.cache;

public class CacheServerMsg implements java.io.Serializable
{
	public CacheServerMsg(char command, String key, CacheObject co)
	{
		this._command = command;
		this._key = key;
		this._cObject = co;
	}
	
	public CacheServerMsg(char command, String key)
	{
		this._command = command;
		this._key = key;
		this._cObject = null;
	}
	
	public CacheServerMsg(char command)
	{
		this._command = command;
		this._key = null;
		this._cObject = null;
	}
	
	public char getCommand()
	{
		return _command;
	}
	public void setCommand(char command)
	{
		this._command = command;
	}
	public String getKey()
	{
		return _key;
	}
	public void setKey(String key)
	{
		this._key = key;
	}
	public CacheObject getcObject()
	{
		return _cObject;
	}
	public void setcObject(CacheObject cObject)
	{
		this._cObject = cObject;
	}


	private char _command;
	private String _key;
	private CacheObject _cObject;
	private static final long serialVersionUID = 3387048399453598095L;

	
}

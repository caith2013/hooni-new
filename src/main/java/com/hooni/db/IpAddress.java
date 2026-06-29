package com.hooni.db;

public class IpAddress implements java.io.Serializable 
{
	
	public IpAddress(){}
	
	public String getIp4()
	{
		return _ip4;
	}
	public void setIp4(String ip4)
	{
		this._ip4 = ip4;
	}
	public String getCountryName()
	{
		return _countryName;
	}
	public void setCountryName(String countryName)
	{
		this._countryName = countryName;
	}
	public String getCountryCode()
	{
		return _countryCode;
	}
	public void setCountryCode(String countryCode)
	{
		this._countryCode = countryCode;
	}
	public String getState()
	{
		return _state;
	}
	public void setState(String state)
	{
		this._state = state;
	}
	public String getCity()
	{
		return _city;
	}
	public void setCity(String city)
	{
		this._city = city;
	}
	public float getLat()
	{
		return _lat;
	}
	public void setLat(float lat)
	{
		this._lat = lat;
	}
	public float getIng()
	{
		return _ing;
	}
	public void setIng(float ing)
	{
		this._ing = ing;
	}
	
	
	public long getIp()
	{
		return _ip;
	}

	public void setIp(long ip)
	{
		this._ip = ip;
	}

	public String getZip()
	{
		return _zip;
	}

	public void setZip(String zip)
	{
		this._zip = zip;
	}
	
	
	public String getAreaCode()
	{
		return _areaCode;
	}

	public void setAreaCode(String areaCode)
	{
		this._areaCode = areaCode;
	}
	
	
	private long _ip;
	private String _ip4;
	private String _countryName;
	private String _countryCode;
	private String _state;
	private String _city;
	private String _zip;
	private String _areaCode;
	private float _lat;
	private float _ing;
	
	private static final long serialVersionUID = 1352029645581740882L;

	
}

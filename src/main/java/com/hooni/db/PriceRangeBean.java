package com.hooni.db;

public class PriceRangeBean implements java.io.Serializable
{
	public PriceRangeBean(int priceLength, int startDigit, int count)
	{
		this._startPrice = calStart(priceLength, startDigit);
		this._endPrice = calEnd(priceLength, startDigit);
		this._count = count;
	}
	
	public static int calStart(int priceLength, int startDigit)
	{
		return (int)Math.pow(10, priceLength-1) * startDigit;
	}
	
	public static int calEnd(int priceLength, int startDigit)
	{
		return (int)Math.round(Math.pow(10, priceLength-1) * (startDigit + 1));
	}
	public PriceRangeBean(float startPrice, float endPrice, int count)
	{
		this._startPrice = startPrice;
		this._endPrice = endPrice;
		this._count = count;
	}
	
	
	public float getStartPrice()
	{
		return _startPrice;
	}

	public void setStartPrice(float startPrice)
	{
		this._startPrice = startPrice;
	}

	public float getEndPrice()
	{
		return _endPrice;
	}

	public void setEndPrice(float endPrice)
	{
		this._endPrice = endPrice;
	}

	public int getCount()
	{
		return _count;
	}

	public void setCount(int count)
	{
		this._count = count;
	}


	private float _startPrice;
	private float _endPrice;
	private int _count;
	
	private static final long serialVersionUID = -121876544476434237L;

}

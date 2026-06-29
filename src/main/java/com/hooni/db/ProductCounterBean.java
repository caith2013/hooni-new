package com.hooni.db;

public class ProductCounterBean implements java.io.Serializable
{

	public ProductCounterBean(int counter, String name)
	{
		this._counter = counter;
		this._name = name;
	}

	public ProductCounterBean(Long counter, String name) {
		// convert Long to int safely; use 0 when null
		this._counter = (counter == null) ? 0 : counter.intValue();
		this._name = name;
	}

	public int getCounter()
	{
		return _counter;
	}
	public void setCounter(int counter)
	{
		this._counter = counter;
	}
	public String getName()
	{
		return _name;
	}
	public void setName(String name)
	{
		this._name = name;
	}
	private int _counter;
	private String _name;
	private static final long serialVersionUID = 3493517241526523670L;

}

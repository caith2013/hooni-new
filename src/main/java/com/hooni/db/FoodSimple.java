package com.hooni.db;

public class FoodSimple
{

	public FoodSimple(long id, String title)
	{
		_id = id;
		_title = title;
	}
	public long getId()
	{
		return _id;
	}
	public void setId(long id)
	{
		this._id = id;
	}
	public String getTitle()
	{
		return _title;
	}
	public void setTitle(String title)
	{
		this._title = title;
	}
	private long _id;
	private String _title;
}

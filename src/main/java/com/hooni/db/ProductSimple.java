package com.hooni.db;

public class ProductSimple implements java.io.Serializable
{

	public ProductSimple(long id, String title, float price, int quantity)
	{
		_id = id;
		_title = title;
		_price = price;
		_quantity=quantity;
	}
	
	public long getId() {
		return _id;
	}
	public void setId(long id) {
		this._id = id;
	}
	
	public String getTitle()
	{
		return _title;
	}
	public void setTitle(String title)
	{
		_title = title;
	}
	
	public float getPrice() {
		return _price;
	}
	public void setPrice(float price) {
		this._price = price;
	}
	
	public int getQuantity() {
		return _quantity;
	}
	public void setQuantity(int quantity) {
		this._quantity = quantity;
	}
	
	private long _id;
	private String _title;
	private float _price;
	private int _quantity;
	
	private static final long serialVersionUID = -129671320159915558L;
}

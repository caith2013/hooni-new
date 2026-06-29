package com.hooni.web.util;

public enum ImagePath {
	PRODUCT("C:\\hooniImages\\products"),
	FOOD("C:\\hooniImages\\foods"),
	NEWS("C:\\hooniImages\\news"),
	SHARE("C:\\hooniImages\\shares");
	
	private ImagePath(String path)
	{
		_path = path;
	}
	
	public String getPath()
	{
		return _path;
	}
	
	
	private String _path;
	public static final ImagePath[] VALUES = values();
}



package com.hooni.web.util;

import java.util.HashMap;

public class ResultBean 
{
	
	public ResultBean(){}
	
	public HashMap<String, String> getUserInputs()
	{
		return _userInputs;
	}
	
	public HashMap<String, String> getErrors()
	{
		return _errors;
	}

	public void saveError(String errorField, String errorMsg)
	{
		_errors.put(errorField, errorMsg);
	}
	
	public void saveUserInputs(String fieldName, String inputText)
	{
		_userInputs.put(fieldName, inputText);
	}
	
	public boolean noError()
	{
		return _errors.isEmpty();
	}
	
	private String _nextUrl = null;
	private HashMap<String, String> _errors = new HashMap<String, String>();
	private HashMap<String, String> _userInputs = new HashMap<String, String>();
}

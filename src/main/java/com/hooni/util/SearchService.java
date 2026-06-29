package com.hooni.util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class SearchService
{
	public SearchService()
	{
		_excecutorService = Executors.newCachedThreadPool();
	}

	
	abstract public <T> List<T> doSearch(String[] keywords);
	
	final ExecutorService _excecutorService;
}

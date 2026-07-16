package com.hooni.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.hooni.db.Product;
import com.hooni.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductSearchService extends SearchService
{
	private final ProductRepository productRepo;

	public ProductSearchService(ProductRepository productRepo)
	{
		super();
		this.productRepo = productRepo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> doSearch(String[] keywords)
	{
		ArrayList<Product> products = new ArrayList<Product>();

		try
		{
			Future<List<Product>> productsByTitle = this._excecutorService.submit(new TitleCallable(keywords, productRepo));
			Future<List<Product>> productsByCat = this._excecutorService.submit(new CategoryCallable(keywords,productRepo));
			Future<List<Product>> productsByUpc = this._excecutorService.submit(new UpcCallable(keywords,productRepo));
			
			products.addAll(productsByTitle.get());
			if(products.isEmpty())
			{
				products.addAll(productsByCat.get());
				if (products.isEmpty())
				{
					products.addAll(productsByUpc.get());
				}
			}
			
		}
		catch(Exception e)
		{
			this._excecutorService.shutdown();
		}
		finally
		{
			this._excecutorService.shutdownNow();
		}
			
			
		
		return products;
	}
	
	public class TitleCallable implements Callable<List<Product>>
	{
		public TitleCallable(String[] keywords, ProductRepository productRepo)
		{
			_keywords = keywords;
			_productRepo = productRepo;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<Product> call() throws Exception
		{
			return _productRepo.findProductsByTitle(_keywords);
		}
		private String[] _keywords;
		private ProductRepository _productRepo;
	}
	
	
	public class CategoryCallable implements Callable<List<Product>>
	{

		public CategoryCallable(String[] keywords, ProductRepository productRepo)
		{
			_keywords =  keywords;
			_productRepo = productRepo;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<Product> call() throws Exception
		{

			return _productRepo.findProductsByCategory(_keywords);
		}
		private String[] _keywords;
		private ProductRepository _productRepo;
		
	}
	
	public class BrandCallable implements Callable<List<Product>>
	{
		private String[] _keywords;
		private ProductRepository _productRepo;

		public BrandCallable(String[] keywords, ProductRepository productRepo)
		{
			_keywords = keywords;
			_productRepo = productRepo;
		}

		@Override
		public List<Product> call() throws Exception
		{
			return _productRepo.findProductsByBrand(_keywords);
		}
		
	}
	
	public class UpcCallable implements Callable<List<Product>>
	{
		private String[] _keywords;
		private ProductRepository _productRepo;

		public UpcCallable(String[] keywords, ProductRepository productRepo)
		{
			_keywords = keywords;
			_productRepo = productRepo;
		}


		@SuppressWarnings("unchecked")
		@Override
		public List<Product> call() throws Exception
		{
			return _productRepo.findProductsByUpc(_keywords);
		}
	}

}

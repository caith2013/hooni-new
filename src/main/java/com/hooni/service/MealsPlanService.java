package com.hooni.service;

import com.hooni.component.CookieComp;
import com.hooni.db.Food;
import com.hooni.db.FoodSimple;
import com.hooni.db.MealType;
import com.hooni.repository.FoodRepository;
import com.hooni.web.util.HttpUtil;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.*;

@Service
@RequestScope
public class MealsPlanService
{
	private Map<String, Cookie> _cookieMap;
	private Cookie _breakfastCookie;
	private Cookie _lunchCookie;
	private Cookie _dinnerCookie;

	private final FoodRepository _foodRepository;
	private final CookieComp _cookieComp;

	public MealsPlanService(CookieComp cookieComp,  FoodRepository foodRepository)
	{
		this._foodRepository = foodRepository;
		this._cookieComp = cookieComp;
		_cookieMap = cookieComp.getCookieMap();

		if (!_cookieMap.containsKey(HOONI_ORG_MEAL_BREAKFAST_CART_COOKIE))
		{
			_breakfastCookie = HttpUtil.makeCookie(HOONI_ORG_MEAL_BREAKFAST_CART_COOKIE, "");
			_breakfastCookie.setMaxAge(24 * 60 * 60); // 24 hours
		}
		else
		{
			_breakfastCookie = _cookieMap.get(HOONI_ORG_MEAL_BREAKFAST_CART_COOKIE);
		}
		
		
		if (!_cookieMap.containsKey(HOONI_ORG_MEAL_LUNCH_CART_COOKIE))
		{
			_lunchCookie = HttpUtil.makeCookie(HOONI_ORG_MEAL_LUNCH_CART_COOKIE, "");
			_lunchCookie.setMaxAge(24 * 60 * 60); // 24 hours
		}
		else
		{
			_lunchCookie = _cookieMap.get(HOONI_ORG_MEAL_LUNCH_CART_COOKIE);
		}
		
		
		if (!_cookieMap.containsKey(HOONI_ORG_MEAL_DINNER_CART_COOKIE))
		{
			_dinnerCookie = HttpUtil.makeCookie(HOONI_ORG_MEAL_DINNER_CART_COOKIE, "");
			_dinnerCookie.setMaxAge(24 * 60 * 60); // 24 hours
		}
		else
		{
			_dinnerCookie = _cookieMap.get(HOONI_ORG_MEAL_DINNER_CART_COOKIE);
		}
		
	}
	
	public void addToCart(MealType type, Food food)
	{
		addToCookie(type, food.getId()+"", food.getTitle());
	}
	
	
	
	public void removeSimpleFoodFromCookie(MealType type, long id)
	{
		removeSimpleFoodFromCookie(type, id+"");
		
	}
	
	public void removeSimpleFoodFromCookie(MealType type, String id)
	{
		String cookieCurrentValue = "";
		Cookie tempCookie = null;
		
		switch(type)
		{
		case BREAKFAST:
			cookieCurrentValue = _breakfastCookie.getValue();
			tempCookie = _breakfastCookie;
			break;
		case LUNCH:
			cookieCurrentValue = _lunchCookie.getValue();
			tempCookie = _lunchCookie;
			break;
		case DINNER:
			cookieCurrentValue = _dinnerCookie.getValue();
			tempCookie = _dinnerCookie;
			break;
		}
		
		
		int start = cookieCurrentValue.indexOf(id+"++");
		int end = cookieCurrentValue.indexOf(COOKIE_TOKEN, start);
		
		//take the product out of the product cookie
		//System.out.println(cookieValue);
		String newCookieValue = cookieCurrentValue.substring(0, start) + cookieCurrentValue.substring(end+2, cookieCurrentValue.length());
		//System.out.println(newCookieValue);
		tempCookie.setValue(newCookieValue);
		HttpUtil.setCookieProperties(tempCookie, ONE_DAY);
		_cookieComp.getResponse().addCookie(tempCookie);
	}
	
	public HashMap<Long,FoodSimple> getFoodSimpleFromCookie(String cookieValue)
	{
		StringTokenizer allFoods = new StringTokenizer(cookieValue, COOKIE_TOKEN);
		HashMap<Long,FoodSimple> fs = new HashMap<Long,FoodSimple>();
		
		while(allFoods.hasMoreElements())
        {
           String oneFood = (String)allFoods.nextElement();
           if (oneFood != null && !oneFood.equals(""))
           {
              StringTokenizer st1 = new StringTokenizer(oneFood,PRODUCT_TOKEN);//id++title++price++quantity::id++title++price+quantity
              if (st1.countTokens() == 2)
              {
                 // then we have one food
            	  FoodSimple foodSimple = parseFoodSimple(st1);
            	  fs.put(new Long(foodSimple.getId()),foodSimple);
              }
           }
        }
		return fs;
	}
	
	private FoodSimple parseFoodSimple(StringTokenizer st)
	{
		 long id=Long.parseLong((String)st.nextElement());
   	  	 String title = (String)st.nextElement();
   	     
   	     return new FoodSimple(id,title);
   	     
   	     
	}
	public List<Food> geFoodFromCookie(Cookie cookie)
	{
		StringTokenizer allFoods = new StringTokenizer(cookie.getValue(), COOKIE_TOKEN);
		ArrayList<Long> fids = new ArrayList<Long>();
		
		while(allFoods.hasMoreElements())
        {
           String oneFood = (String)allFoods.nextElement();
           if (oneFood != null && !oneFood.equals(""))
           {
              StringTokenizer st1 = new StringTokenizer(oneFood,PRODUCT_TOKEN);//id++title::id++title
              if (st1.countTokens() == 2)
              {
                 // then we have one food
            	 fids.add(new Long((String)st1.nextElement()));
              }
           }
        }
		return getFoods(fids);
	}
	
	public List<Food> getFoods(List<Long> fids)
	{
		if (fids.isEmpty()) return null;
		
		try
		{
			return _foodRepository.findAllById(fids);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

	}
	public List<Food> getFoods(Set<Long> fids)
	{
		try
		{
			return _foodRepository.findAllById(fids);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	

	public void addToCookie(MealType type, long id, String title)
	{
		
		String cookieCurrentValue = "";
		Cookie tempCookie = null;
		
		switch(type)
		{
		case BREAKFAST:
			cookieCurrentValue = _breakfastCookie.getValue();
			tempCookie = _breakfastCookie;
			break;
		case LUNCH:
			cookieCurrentValue = _lunchCookie.getValue();
			tempCookie = _lunchCookie;
			break;
		case DINNER:
			cookieCurrentValue = _dinnerCookie.getValue();
			tempCookie = _dinnerCookie;
			break;
		}
		
		HashMap<Long,FoodSimple> foods = this.getFoodSimpleFromCookie(cookieCurrentValue);
		
		Long key = new Long(id);
		if (foods.containsKey(key))
		{
			FoodSimple fs = foods.get(key);
			foods.put(key, fs);
			String newCookieValue="";
			for (Long pkey : foods.keySet())
			{
				fs = foods.get(pkey);
				newCookieValue += fs.getId() + PRODUCT_TOKEN + fs.getTitle() + COOKIE_TOKEN;
			}
			tempCookie.setValue(newCookieValue);
		}
		else
		{
			cookieCurrentValue = cookieCurrentValue + id + PRODUCT_TOKEN + title + COOKIE_TOKEN;
			tempCookie.setValue(cookieCurrentValue);
		}
		HttpUtil.setCookieProperties(tempCookie, ONE_DAY);
		_cookieComp.getResponse().addCookie(tempCookie);
	}
	
	
	public void addToCookie(MealType type, String id, String title)
	{
		addToCookie(type, Long.parseLong(id), title);
	}
	
	public Cookie getCookie(MealType type)
	{
		switch(type)
		{
		case BREAKFAST:
			return _breakfastCookie;
		case LUNCH:
			return _lunchCookie;
		case DINNER:
			return _dinnerCookie;
		}
		return null;
	}
	
	private final static int ONE_DAY = 24 * 60 * 60;
	
	
	private final static String HOONI_ORG_MEAL_BREAKFAST_CART_COOKIE = "meal_breakfast_cart";
	private final static String HOONI_ORG_MEAL_LUNCH_CART_COOKIE = "meal_lunch_cart";
	private final static String HOONI_ORG_MEAL_DINNER_CART_COOKIE = "meal_dinner_cart";
	

	private final static String HOONI_ORG_SHOPPING_CART_COOKIE = "shopping_cart_hooni.org";
	private final static String SHOPPING_CART_TOTAL_SHIPPING_COOKIE = "shopping_cart_total_shipping";
	//private final static String SHOPPING_CART_SHIPPING_COOKIE = "shopping_cart_shipping";
	private final static String COOKIE_TOKEN = "::";
	private final static String PRODUCT_TOKEN = "++";
	
	
}

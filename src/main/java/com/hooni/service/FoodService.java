package com.hooni.service;

import com.hooni.db.Food;
import com.hooni.db.MealType;
import com.hooni.repository.FoodRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Service
public class FoodService
{
    @Autowired
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }


    public Food getMyDay(List<Food> foods)
    {
        List<Food> result;
        Random r = new Random();
        int i = 0;
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        if (calendar.get(Calendar.AM_PM) == Calendar.AM && calendar.get(Calendar.HOUR) <=11)
        {
            result = foodRepository.getFoodsByMealType(MealType.BREAKFAST.ordinal());

        }
        else if ((calendar.get(Calendar.AM_PM) == Calendar.AM && calendar.get(Calendar.HOUR) > 11) || (calendar.get(Calendar.AM_PM) == Calendar.PM && calendar.get(Calendar.HOUR) <= 3))
        {
            result = foodRepository.getFoodsByMealType(MealType.LUNCH.ordinal());
        }
        else if (calendar.get(Calendar.AM_PM) == Calendar.PM && calendar.get(Calendar.HOUR) >= 5 && calendar.get(Calendar.HOUR) < 12 )
        {
            result = foodRepository.getFoodsByMealType(MealType.DINNER.ordinal());
        }
        else
        {
            result = foods;
        }

        i = r.nextInt(result.size());
        return result.get(i);
    }

    private final static String HOONI_ORG_MEAL_BREAKFAST_CART_COOKIE = "meal_breakfast_cart";
    private final static String HOONI_ORG_MEAL_LUNCH_CART_COOKIE = "meal_lunch_cart";
    private final static String HOONI_ORG_MEAL_DINNER_CART_COOKIE = "meal_dinner_cart";

    private Cookie _breakfastCookie;
    private Cookie _lunchCookie;
    private Cookie _dinnerCookie;
}

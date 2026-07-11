// File: FoodRepositoryCustom.java
package com.hooni.repository;

import com.hooni.db.Food;
import java.util.List;

public interface FoodRepositoryCustom {
    List<Food> getFoods(String[] mealType, String foodType, String[] keywords);
}
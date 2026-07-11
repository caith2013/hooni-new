// File: FoodRepositoryImpl.java
package com.hooni.repository;

import com.hooni.db.Food;
import com.hooni.db.FoodGoodFor;
import com.hooni.db.FoodKeyword;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FoodRepositoryImpl implements FoodRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Food> getFoods(String[] mealType, String foodType, String[] keywords) {
        StringBuilder query = new StringBuilder("select distinct food from Food food");
        List<String> conditions = new ArrayList<>();

        // Filter by foodType if provided and not "ALL"
        if (foodType != null && !foodType.trim().isEmpty()) {
            try {
                int typeValue = Integer.parseInt(foodType);
                // Assuming FoodType.ALL has a specific value (typically 0), adjust as needed
                if (typeValue != 0) {
                    conditions.add("food.kind = " + typeValue);
                }
            } catch (NumberFormatException e) {
                // Invalid foodType, skip this filter
            }
        }

        // Filter by mealType using joins
        if (mealType != null && mealType.length > 0) {
            StringBuilder mealTypeCondition = new StringBuilder();
            for (int i = 0; i < mealType.length; i++) {
                query.append(" join food.foodGoodFor fgf").append(i);
                if (i > 0) {
                    mealTypeCondition.append(" or ");
                }
                mealTypeCondition.append("fgf").append(i).append(".mealType = ").append(mealType[i]);
            }
            if (mealTypeCondition.length() > 0) {
                conditions.add("(" + mealTypeCondition + ")");
            }
        }

        // Filter by keywords using joins
        if (keywords != null && keywords.length > 0) {
            StringBuilder keywordCondition = new StringBuilder();
            for (int i = 0; i < keywords.length; i++) {
                if (keywords[i] != null && !keywords[i].trim().isEmpty()) {
                    query.append(" join food.foodKeywords fk").append(i);
                    if (keywordCondition.length() > 0) {
                        keywordCondition.append(" or ");
                    }
                    keywordCondition.append("fk").append(i).append(".keyword = '").append(keywords[i]).append("'");
                }
            }
            if (keywordCondition.length() > 0) {
                conditions.add("(" + keywordCondition + ")");
            }
        }

        // Build the final query
        if (!conditions.isEmpty()) {
            query.append(" where ");
            query.append(String.join(" and ", conditions));
        } else {
            // If no filters provided, return empty list or all foods based on your requirement
            return new ArrayList<>();
        }

        TypedQuery<Food> typedQuery = em.createQuery(query.toString(), Food.class);
        return typedQuery.getResultList();
    }
}
package com.hooni.db;

import com.hooni.repository.FoodRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FoodSQLs — migrated to Spring.
 *
 * Original used Hibernate Session / criteria API.
 * Now delegates to FoodRepository (Spring Data JPA).
 */
@Service
public class FoodSQLs {

    private final FoodRepository foodRepository;

    public FoodSQLs(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Food getFood(long id) {
        return foodRepository.findById(id).orElse(null);
    }

    public void saveWithCommit(Food food) {
        foodRepository.save(food);
    }

    public List<Food> getFoods(int from, int size) {
        return foodRepository.findAllByOrderByTimeCreatedDesc(PageRequest.of(from / size, size));
    }

    public List<Food> getTodaySpecials() {
        return foodRepository.findTodaySpecials();
    }

    public List<Food> getFavoriteFoods(User user) {
        return foodRepository.findFavoriteFoodsByUsername(user.getUserName());
    }
}

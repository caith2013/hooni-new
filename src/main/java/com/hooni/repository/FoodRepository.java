package com.hooni.repository;

import com.hooni.db.Food;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryCustom {

    List<Food> findAllByOrderByTimeCreatedDesc(Pageable pageable);

    @Query("select ff.food from UserFavoriteFood ff where ff.user.userName = :username")
    List<Food> findFavoriteFoodsByUsername(String username);

    @Query(value = "select * from (select * from FOOD f order by f.time desc) as food group by food.kind",
           nativeQuery = true)
    List<Food> findTodaySpecials();

    List<Food> findAllById(Iterable<Long> ids);

    @Query(value = "select distinct f from Food f " +
            "join f.foodGoodFor fgf " +
            "where fgf.mealType = :mealType " +
            "order by f.timeCreated desc limit 6")
    List<Food> getFoodsByMealType(@Param("mealType") int mealType);
}

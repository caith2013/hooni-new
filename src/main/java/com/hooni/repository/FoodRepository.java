package com.hooni.repository;

import com.hooni.db.Food;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryCustom {

    @Cacheable("foodsByTimeCreatedDesc")
    List<Food> findAllByOrderByTimeCreatedDesc(Pageable pageable);

    @Query("select ff.food from UserFavoriteFood ff where ff.user.userName = :username")
    List<Food> findFavoriteFoodsByUsername(String username);

    @Cacheable("todaySpecials")
    @Query(value = "select f1.* from food f1 " +
            "left join food f2 on f1.kind = f2.kind " +
            "and (f2.time > f1.time or (f2.time = f1.time and f2.id > f1.id)) " +
            "where f2.id is null " +
            "order by f1.time desc",
           nativeQuery = true)
    List<Food> findTodaySpecials();

    List<Food> findAllById(Iterable<Long> ids);

    @Query(value = "select distinct f from Food f " +
            "join f.foodGoodFor fgf " +
            "where fgf.mealType = :mealType " +
            "order by f.timeCreated desc limit 6")
    List<Food> getFoodsByMealType(@Param("mealType") int mealType);
}

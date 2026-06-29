package com.hooni.repository;

import com.hooni.db.UserFavoriteFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteFoodRepository extends JpaRepository<UserFavoriteFood, UserFavoriteFood.UserFavoriteFoodId> {
    List<UserFavoriteFood> findByUserUserName(String username);
}

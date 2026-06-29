package com.hooni.db;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/** Table: user_favorite_food — composite PK (user_username, food_id) */
@Entity
@Table(name = "user_favorite_food")
@IdClass(UserFavoriteFood.UserFavoriteFoodId.class)
public class UserFavoriteFood implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_username")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_id")
    private Food food;

    public User getUser()          { return user; }
    public void setUser(User user) { this.user = user; }
    public Food getFood()          { return food; }
    public void setFood(Food food) { this.food = food; }

    public static class UserFavoriteFoodId implements Serializable {
        private User user;
        private Food food;
        public UserFavoriteFoodId() {}
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserFavoriteFoodId that)) return false;
            return Objects.equals(user, that.user) && Objects.equals(food, that.food);
        }
        @Override public int hashCode() { return Objects.hash(user, food); }
    }
}

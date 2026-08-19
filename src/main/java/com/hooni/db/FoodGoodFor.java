package com.hooni.db;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/** Table: good_for — composite PK (meal_time, food_id) */
@Entity
@Table(name = "good_for")
@IdClass(FoodGoodFor.FoodGoodForId.class)
public class FoodGoodFor implements Serializable {

    @Id
    @Column(name = "meal_time")
    private int mealType;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    public int  getMealType()          { return mealType; }
    public void setMealType(int mt)    { this.mealType = mt; }
    public Food getFood()              { return food; }
    public void setFood(Food food)     { this.food = food; }

    /** Composite PK class required by @IdClass */
    public static class FoodGoodForId implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private int mealType;
        private Food food;
        public FoodGoodForId() {}
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FoodGoodForId that)) return false;
            return mealType == that.mealType && Objects.equals(food, that.food);
        }
        @Override public int hashCode() { return Objects.hash(mealType, food); }
    }
}

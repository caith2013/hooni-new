package com.hooni.db;

import jakarta.persistence.*;

import java.io.Serializable;

/** Table: food_keywords */
@Entity
@Table(name = "food_keywords")
public class FoodKeyword implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(name = "keyword")
    private String keyword;

    public FoodKeyword() {}
    public FoodKeyword(String keyword) { this.keyword = keyword; }

    public long   getId()              { return id; }
    public Food   getFood()            { return food; }
    public void   setFood(Food food)   { this.food = food; }
    public String getKeyword()         { return keyword; }
    public void   setKeyword(String k) { this.keyword = k; }
}

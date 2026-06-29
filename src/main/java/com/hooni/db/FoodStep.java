package com.hooni.db;

import jakarta.persistence.*;

import java.io.Serializable;

/** Table: prepare_steps */
@Entity
@Table(name = "prepare_steps")
public class FoodStep implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "picture", nullable = false)
    private boolean hasPicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "foodid", nullable = false)
    private Food food;

    public FoodStep() {}
    public FoodStep(String description, boolean hasPicture) {
        this.description = description;
        this.hasPicture = hasPicture;
    }

    public long    getId()                     { return id; }
    public String  getDescription()            { return description; }
    public void    setDescription(String d)    { this.description = d; }
    public boolean getHasPicture()             { return hasPicture; }
    public void    setHasPicture(boolean hp)   { this.hasPicture = hp; }
    public Food    getFood()                   { return food; }
    public void    setFood(Food food)          { this.food = food; }
}

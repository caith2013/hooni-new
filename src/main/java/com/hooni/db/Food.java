package com.hooni.db;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * JPA-annotated Food entity.
 * Replaces Food.java + Food.hbm.xml.
 * Table: FOOD
 */
@Entity
@Table(name = "food")
public class Food implements Serializable {

    private static final long serialVersionUID = -7538530978262742317L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "picture", nullable = false)
    private boolean hasPicture;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCreated;

    @Column(name = "lastModified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    @Column(name = "kind")
    private byte kind;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "user_username", nullable = false)
    private User user;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id asc")
    private Set<FoodStep> foodSteps;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id asc")
    private Set<FoodKeyword> foodKeywords;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<FoodGoodFor> foodGoodFor;

    public Food() {}

    public Food(String title, String description, boolean picture, Date date, Date lastModified) {
        this.title = title;
        this.description = description;
        this.hasPicture = picture;
        this.timeCreated = date;
        this.lastModified = lastModified;
    }

    public long getId()                                    { return id; }
    @SuppressWarnings("unused") private void setId(long id){ this.id = id; }

    public String getTitle()                               { return title; }
    public void   setTitle(String title)                   { this.title = title; }

    public String getDescription()                         { return description; }
    public void   setDescription(String text)              { this.description = text; }

    public boolean getHasPicture()                         { return hasPicture; }
    public void    setHasPicture(boolean hasPicture)       { this.hasPicture = hasPicture; }

    public Date getTimeCreated()                           { return timeCreated; }
    public void setTimeCreated(Date timeCreated)           { this.timeCreated = timeCreated; }

    public Date getLastModified()                          { return lastModified; }
    public void setLastModified(Date lastModified)         { this.lastModified = lastModified; }

    public int  getKind()                                  { return kind; }
    public void setKind(int kind)                          { this.kind = (byte)kind; }

    public User getUser()                                  { return user; }
    public void setUser(User user)                         { this.user = user; }

    public Set<FoodStep> getFoodSteps() {
        if (foodSteps == null) foodSteps = new LinkedHashSet<>();
        return foodSteps;
    }
    public void setFoodSteps(Set<FoodStep> steps)          { this.foodSteps = steps; }
    public void addFoodStep(FoodStep step) {
        step.setFood(this);
        getFoodSteps().add(step);
    }

    public Set<FoodKeyword> getFoodKeywords() {
        if (foodKeywords == null) foodKeywords = new LinkedHashSet<>();
        return foodKeywords;
    }
    public void setFoodKeywords(Set<FoodKeyword> foodKeywords) { this.foodKeywords = foodKeywords; }
    public void addFoodKeyword(FoodKeyword foodKeyword) {
        foodKeyword.setFood(this);
        getFoodKeywords().add(foodKeyword);
    }

    public Set<FoodGoodFor> getFoodGoodFor() {
        if (foodGoodFor == null) foodGoodFor = new LinkedHashSet<>();
        return foodGoodFor;
    }
    public void setFoodGoodFor(Set<FoodGoodFor> foodGoodFor) { this.foodGoodFor = foodGoodFor; }
    public void addFoodGoodFor(FoodGoodFor foodGoodFor) {
        foodGoodFor.setFood(this);
        getFoodGoodFor().add(foodGoodFor);
    }
}

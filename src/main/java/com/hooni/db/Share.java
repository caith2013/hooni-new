package com.hooni.db;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/** Table: SHARES */
@Entity
@Table(name = "shares")
public class Share implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private long id;
    @Column(name = "title")   private String title;
    @Column(name = "offer")   private String offer;
    @Column(name = "time") @Temporal(TemporalType.TIMESTAMP) private Date timeCreated;
    @Column(name = "rate")    private String byRate;
    @Column(name = "price")   private java.math.BigDecimal price;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "user_username", nullable = false) @Fetch(FetchMode.JOIN) private User user;
    @OneToMany(mappedBy = "share", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @OrderBy("id asc")
    private Set<Blog> blogs;

    public Share() {}
    public long   getId()                 { return id; }
    public String getTitle()              { return title; }
    public void   setTitle(String v)      { this.title = v; }
    public String getOffer()              { return offer; }
    public void   setOffer(String v)      { this.offer = v; }
    public Date   getTimeCreated()        { return timeCreated; }
    public void   setTimeCreated(Date v)  { this.timeCreated = v; }
    public String getByRate()             { return byRate; }
    public void   setByRate(String v)     { this.byRate = v; }
    public java.math.BigDecimal getPrice()              { return price; }
    public void   setPrice(java.math.BigDecimal v)      { this.price = v; }
    public User   getUser()               { return user; }
    public void   setUser(User v)         { this.user = v; }
    public Set<Blog> getBlogs() {
        if (blogs == null) blogs = new LinkedHashSet<>();
        return blogs;
    }
    public void setBlogs(Set<Blog> blogs) { this.blogs = blogs; }
}

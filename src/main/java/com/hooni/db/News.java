package com.hooni.db;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

/** Table: news */
@Entity
@Table(name = "news")
public class News implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private long id;
    @Column(name = "title")          private String title;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "user_username", nullable = false) private User user;
    @Column(name = "time") @Temporal(TemporalType.TIMESTAMP) private Date timeCreated;
    @Column(name = "content", columnDefinition = "TEXT") private String contents;
    @Column(name = "picture", nullable = false) private int numPicture;

    public News() {}
    public long   getId()                    { return id; }
    public String getTitle()                 { return title; }
    public void   setTitle(String v)         { this.title = v; }
    public User   getUser()                  { return user; }
    public void   setUser(User v)            { this.user = v; }
    public Date   getTimeCreated()           { return timeCreated; }
    public void   setTimeCreated(Date v)     { this.timeCreated = v; }
    public String getContents()              { return contents; }
    public void   setContents(String v)      { this.contents = v; }
    public int    getNumPicture()            { return numPicture; }
    public void   setNumPicture(int v)       { this.numPicture = v; }
}

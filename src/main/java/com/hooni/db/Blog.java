package com.hooni.db;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

/** Table: blog */
@Entity
@Table(name = "blog")
public class Blog implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "shares_id", nullable = false) private Share share;
    @Column(name = "content", columnDefinition = "TEXT") private String content;
    @Column(name = "picture", nullable = false) private int numPicture;
    @Column(name = "time") @Temporal(TemporalType.TIMESTAMP) private Date timeCreated;

    public Blog() {}
    public long   getId()                    { return id; }
    public Share  getShare()                 { return share; }
    public void   setShare(Share v)          { this.share = v; }
    public String getContent()               { return content; }
    public void   setContent(String v)       { this.content = v; }
    public int    getNumPicture()            { return numPicture; }
    public void   setNumPicture(int v)       { this.numPicture = v; }
    public Date   getTimeCreated()           { return timeCreated; }
    public void   setTimeCreated(Date v)     { this.timeCreated = v; }
}

package com.hooni.db;

import jakarta.persistence.*;

import java.io.Serializable;

/** Table: ad_keywords (inferred from Ads mapping) */
@Entity
@Table(name = "ad_keywords")
public class AdKeyword implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "ads_id", nullable = false) private Ads ads;
    @Column(name = "keyword") private String keyword;

    public AdKeyword() {}
    public long   getId()              { return id; }
    public Ads    getAds()             { return ads; }
    public void   setAds(Ads v)        { this.ads = v; }
    public String getKeyword()         { return keyword; }
    public void   setKeyword(String v) { this.keyword = v; }
}

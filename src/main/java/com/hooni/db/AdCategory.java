package com.hooni.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

/** Table: ad_category (inferred from Ads mapping) */
@Entity
@Table(name = "ad_category")
public class AdCategory implements Serializable {
    @Id
    @Column(name = "name")
    private String name;

    public AdCategory() {}
    public String getName()         { return name; }
    public void   setName(String v) { this.name = v; }
}

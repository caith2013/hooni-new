package com.hooni.db;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/** Table: product */
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "pid") private long id;
    @Column(name = "title", nullable = false)       private String title;
    @Column(name = "description", nullable = false, columnDefinition = "TEXT") private String description;
    @Column(name = "upc")                           private String upc;
    @Column(name = "cat", nullable = false)         private String category;
    @Column(name = "brand", nullable = false)       private String brand;
    @Column(name = "price", nullable = false)       private java.math.BigDecimal price;
    @Column(name = "weight")                        private int weight;
    @Column(name = "width")                         private int width;
    @Column(name = "length")                        private int length;
    @Column(name = "height")                        private int height;
    @Column(name = "quantity", nullable = false)    private int quantity;
    @Column(name = "warranty")                      private String warranty;
    @Column(name = "shipping")                      private String shipping;
    @Column(name = "shipping_cost")                 private java.math.BigDecimal shippingCost;
    @Column(name = "final_price")                   private BigDecimal finalPrice;
    @Column(name = "creation_date", nullable = false) @Temporal(TemporalType.TIMESTAMP) private Date creationDate;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "user_username", nullable = false) @Fetch(FetchMode.JOIN) private User user;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AgentHasProduct> agentHasProduct;

    public Product() {}
    public long   getId()                        { return id; }
    public String getTitle()                     { return title; }
    public void   setTitle(String v)             { this.title = v; }
    public String getDescription()               { return description; }
    public void   setDescription(String v)       { this.description = v; }
    public String getUpc()                       { return upc; }
    public void   setUpc(String v)               { this.upc = v; }
    public String getCategory()                  { return category; }
    public void   setCategory(String v)          { this.category = v; }
    public String getBrand()                     { return brand; }
    public void   setBrand(String v)             { this.brand = v; }
    public java.math.BigDecimal getPrice()       { return price; }
    public void   setPrice(java.math.BigDecimal v) { this.price = v; }
    public int  getWeight()                    { return weight; }
    public void   setWeight(int v)             { this.weight = v; }
    public int  getWidth()                     { return width; }
    public void   setWidth(int v)              { this.width = v; }
    public int    getLength()                    { return length; }
    public void   setLength(int v)               { this.length = v; }
    public int    getHeight()                    { return height; }
    public void   setHeight(int v)               { this.height = v; }
    public int    getQuantity()                  { return quantity; }
    public void   setQuantity(int v)             { this.quantity = v; }
    public String getWarranty()                  { return warranty; }
    public void   setWarranty(String v)          { this.warranty = v; }
    public String getShipping()                  { return shipping; }
    public void   setShipping(String v)          { this.shipping = v; }
    public java.math.BigDecimal  getShippingCost()              { return shippingCost; }
    public void   setShippingCost(java.math.BigDecimal v)       { this.shippingCost = v; }
    public BigDecimal getFinalPrice()            { return finalPrice; }
    public void   setFinalPrice(BigDecimal v)    { this.finalPrice = v; }
    public Date   getCreationDate()              { return creationDate; }
    public void   setCreationDate(Date v)        { this.creationDate = v; }
    public User   getUser()                      { return user; }
    public void   setUser(User v)                { this.user = v; }
    public Set<AgentHasProduct> getAgentHasProduct() { return agentHasProduct; }
    public void setAgentHasProduct(Set<AgentHasProduct> v) { this.agentHasProduct = v; }
}

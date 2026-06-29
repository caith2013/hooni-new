package com.hooni.db;

import jakarta.persistence.*;

import java.io.Serializable;

/** Table: agent */
@Entity
@Table(name = "agent")
public class Agent implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private long id;
    @Column(name = "name")    private String name;
    @Column(name = "address") private String address;
    @Column(name = "city")    private String city;
    @Column(name = "state")   private String state;
    @Column(name = "zip")     private String zip;
    @Column(name = "country") private String country;
    @Column(name = "manager") private String manager;
    @Column(name = "phone")   private String phone;
    @Column(name = "email")   private String email;

    public Agent() {}
    public long   getId()               { return id; }
    public String getName()             { return name; }
    public void   setName(String v)     { this.name = v; }
    public String getAddress()          { return address; }
    public void   setAddress(String v)  { this.address = v; }
    public String getCity()             { return city; }
    public void   setCity(String v)     { this.city = v; }
    public String getState()            { return state; }
    public void   setState(String v)    { this.state = v; }
    public String getZip()              { return zip; }
    public void   setZip(String v)      { this.zip = v; }
    public String getCountry()          { return country; }
    public void   setCountry(String v)  { this.country = v; }
    public String getManager()          { return manager; }
    public void   setManager(String v)  { this.manager = v; }
    public String getPhone()            { return phone; }
    public void   setPhone(String v)    { this.phone = v; }
    public String getEmail()            { return email; }
    public void   setEmail(String v)    { this.email = v; }
}

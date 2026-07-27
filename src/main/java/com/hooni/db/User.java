package com.hooni.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

/**
 * JPA-annotated User entity.
 * Replaces User.java + User.hbm.xml from the original Hibernate 3 setup.
 * Table: USER  |  PK: username (assigned, not generated)
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -7719548629117209364L;

    @Id
    @Column(name = "username", length = 45, nullable = false)
    private String userName;

    @Column(name = "firstname") private String firstName;
    @Column(name = "lastname")  private String lastName;
    @Column(name = "phone")     private String phone;
    @Column(name = "cell")      private String cell;
    @Column(name = "email")     private String email;
    @Column(name = "aim")       private String aim;
    @Column(name = "skype")     private String skype;
    @Column(name = "webpage")   private String webpage;
    @Column(name = "age")       private int age;
    @Column(name = "picture")   private String profileImage;
    @Column(name = "password")  private String password;
    @Column(name = "status")    private String status;

    public User() {}

    public User(String username, String email, String firstName, String lastName,
                String phone, String cell, String aim, String skype,
                String webpage, int age, String url, String password, String status) {
        this.userName = username; this.email = email; this.firstName = firstName;
        this.lastName = lastName; this.phone = phone; this.cell = cell;
        this.aim = aim; this.skype = skype; this.webpage = webpage;
        this.age = age; this.profileImage = url; this.password = password;
        this.status = status;
    }

    public String getUserName()                        { return userName; }
    public void   setUserName(String v)                { this.userName = v; }
    public String getFirstName()                       { return firstName; }
    public void   setFirstName(String v)               { this.firstName = v; }
    public String getLastName()                        { return lastName; }
    public void   setLastName(String v)                { this.lastName = v; }
    public String getPhone()                           { return phone; }
    public void   setPhone(String v)                   { this.phone = v; }
    public String getCell()                            { return cell; }
    public void   setCell(String v)                    { this.cell = v; }
    public String getEmail()                           { return email; }
    public void   setEmail(String v)                   { this.email = v; }
    public String getAim()                             { return aim; }
    public void   setAim(String v)                     { this.aim = v; }
    public String getSkype()                           { return skype; }
    public void   setSkype(String v)                   { this.skype = v; }
    public String getWebpage()                         { return webpage; }
    public void   setWebpage(String v)                 { this.webpage = v; }
    public int    getAge()                             { return age; }
    public void   setAge(int v)                        { this.age = v; }
    public String getProfileImage()                    { return profileImage; }
    public void   setProfileImage(String v)            { this.profileImage = v; }
    public String getPassword()                        { return password; }
    public void   setPassword(String v)                { this.password = v; }
    public String getStatus()                          { return status; }
    public void   setStatus(String v)                  { this.status = v; }
}

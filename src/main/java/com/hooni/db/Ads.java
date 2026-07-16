package com.hooni.db;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/** Table: ADS */
@Entity
@Table(name = "ADS")
public class Ads implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private long id;
    @Column(name = "subject")        private String subject;
    @Column(name = "description", columnDefinition = "TEXT") private String description;
    @Column(name = "contact_infor")  private String contactInfor;
    @Column(name = "personal", nullable = false) private boolean personal;
    @Column(name = "time") @Temporal(TemporalType.TIMESTAMP) private Date timeCreated;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "user_username", nullable = false) @Fetch(FetchMode.JOIN) private User user;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ad_category_name", nullable = false) private AdCategory adcat;
    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private Set<AdKeyword> adKeywords;

    public Ads() {}
    public long       getId()                  { return id; }
    public String     getSubject()             { return subject; }
    public void       setSubject(String v)     { this.subject = v; }
    public String     getDescription()         { return description; }
    public void       setDescription(String v) { this.description = v; }
    public String     getContactInfor()        { return contactInfor; }
    public void       setContactInfor(String v){ this.contactInfor = v; }
    public boolean    getPersonal()            { return personal; }
    public void       setPersonal(boolean v)   { this.personal = v; }
    public Date       getTimeCreated()         { return timeCreated; }
    public void       setTimeCreated(Date v)   { this.timeCreated = v; }
    public User       getUser()                { return user; }
    public void       setUser(User v)          { this.user = v; }
    public AdCategory getAdcat()               { return adcat; }
    public void       setAdcat(AdCategory v)   { this.adcat = v; }
    public Set<AdKeyword> getAdKeywords() {
        if (adKeywords == null) adKeywords = new LinkedHashSet<>();
        return adKeywords;
    }
    public void setAdKeywords(Set<AdKeyword> v) { this.adKeywords = v; }
}

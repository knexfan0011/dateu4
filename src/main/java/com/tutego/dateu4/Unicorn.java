package com.tutego.dateu4;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class Unicorn {
    @PersistenceContext

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String email;
    public String password;
    //public @Column(name = "profile_fk")
    //Long profileFk;

    public @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "profile_fk")//, referencedColumnName = "ID"
        Profile profile;

    public Unicorn(){};

    public Unicorn(String email, String password, Profile profile){
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Unicorn{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profile=" + profile +
                '}';
    }
}


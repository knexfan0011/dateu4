package com.tutego.dateu4;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Access(AccessType.FIELD)
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    //@Column(name="profile_fk") Long profileFk;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROFILE_FK")@JsonBackReference
    public Profile profileFk;
    String name;
    @Column(name = "is_profile_photo")
    boolean isProfilePhoto;
    String description;
    LocalDateTime created;

    @Override
    public String toString() {
        return String.format("Photo{id=%d, name='%s', " +
                        "description='%s', created=%s, isProfilePhoto=%s}",
                id /*⚠*/, name, description, created, isProfilePhoto);
    }
}
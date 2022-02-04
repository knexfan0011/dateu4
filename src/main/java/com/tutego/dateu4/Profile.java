package com.tutego.dateu4;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*; // -(1)
import java.time.*;
import java.util.List;

@Entity
@Access(AccessType.FIELD) // - (2) (3)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // - (4) (5)
    public Long id;
    public String nickname;
    public LocalDate birthdate;
    public short hornlength;
    public short gender;
    public @Column(name = "attracted_to_gender")
        Short attractedToGender;// -(6) (7)
    public String description;
    public LocalDateTime lastseen;

    @OneToMany(mappedBy = "profileFk", fetch = FetchType.EAGER)
    @JsonManagedReference
    public List<Photo> photos;

    public Profile(String nickname, LocalDate birthdate, short hornlength, short gender, Short attractedToGender, String description, LocalDateTime lastseen){
        this.nickname = nickname;
        this.birthdate = birthdate;
        this.hornlength = hornlength;
        this.gender = gender;
        this.attractedToGender = attractedToGender;
        this.description = description;
        this.lastseen = lastseen;
    }

    public Profile(Long id, String nickname, LocalDate birthdate, short hornlength, short gender, Short attractedToGender, String description, LocalDateTime lastseen){
        this.id = id;
        this.nickname = nickname;
        this.birthdate = birthdate;
        this.hornlength = hornlength;
        this.gender = gender;
        this.attractedToGender = attractedToGender;
        this.description = description;
        this.lastseen = lastseen;
    }

    public Profile(){}

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", birthdate=" + birthdate +
                ", hornlength=" + hornlength +
                ", gender=" + gender +
                ", attractedToGender=" + attractedToGender +
                ", description='" + description + '\'' +
                ", lastseen=" + lastseen +
                //", photos=" + (photos == null ? "PHOTOS_IS_NULL" : lstToStr(photos)) +
                '}';
    }

    private String lstToStr(List<Photo> lst){
        String out = "";
        for (var elem: lst) {
            out += elem.toString();
        }
        return out;
    }
}
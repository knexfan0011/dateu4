package com.tutego.dateu4;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*; // -(1)
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ProfileFormData {
    private long id;
    private String nickname;

    private String password;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    private short hornlength;
    private short gender;
    private @Column(name = "attracted_to_gender") Short attractedToGender;
    private String description;
    private LocalDateTime lastseen;

    //private List<Photo> photos;


    @Override
    public String toString() {
        return "ProfileFormData{" +
                "id=" + id +
                ", nickname='" + nickname +
                ", password='" + password +
                ", email='" + email + '\'' +
                ", birthdate=" + birthdate +
                ", hornlength=" + hornlength +
                ", gender=" + gender +
                ", attractedToGender=" + attractedToGender +
                ", description='" + description + '\'' +
                ", lastseen=" + lastseen +
                '}';
    }

    public ProfileFormData(){}

    public ProfileFormData(Profile profile){
        this.id = profile.id;
        this.nickname = profile.nickname;
        this.birthdate = profile.birthdate;
        this.hornlength = profile.hornlength;
        this.gender = profile.gender;
        this.attractedToGender = profile.attractedToGender;
        this.description = profile.description;
        this.lastseen = profile.lastseen;
    }

    public ProfileFormData(long id, String nickname, LocalDate birthdate, short hornlength, short gender, short attractedToGender, String description, LocalDateTime lastseen){
        this.id = id;
        this.nickname = nickname;
        this.birthdate = birthdate;
        this.hornlength = hornlength;
        this.gender = gender;
        this.attractedToGender = attractedToGender;
        this.description = description;
        this.lastseen = lastseen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }



    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public short getHornlength() {
        return hornlength;
    }

    public void setHornlength(short hornlength) {
        this.hornlength = hornlength;
    }

    public short getGender() {
        return gender;
    }

    public void setGender(short gender) {
        this.gender = gender;
    }

    public Short getAttractedToGender() {
        return attractedToGender;
    }

    public void setAttractedToGender(Short attractedToGender) {
        this.attractedToGender = attractedToGender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLastseen() {
        return lastseen;
    }

    public void setLastseen(LocalDateTime lastseen) {
        this.lastseen = lastseen;
    }

    public Profile getNewProfile() {
        return new Profile(this.nickname, this.birthdate, this.hornlength, this.gender, this.attractedToGender, this.description, this.lastseen == null ? LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) : this.lastseen);
    }
    public Profile getProfile() {
        return new Profile(this.id, this.nickname, this.birthdate, this.hornlength, this.gender, this.attractedToGender, this.description, this.lastseen == null ? LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) : this.lastseen);
    }


    public Unicorn getNewUnicorn(Profile profile) {
        return new Unicorn(email, "{noop}"+password, profile);
    }
    public Unicorn getUnicorn(Profile profile) {
        return new Unicorn(email, "{noop}"+password, profile);
    }

}

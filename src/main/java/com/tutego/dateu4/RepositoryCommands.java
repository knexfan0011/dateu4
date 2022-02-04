package com.tutego.dateu4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

@ShellComponent
public class RepositoryCommands {
    @Autowired
    ProfileRepository profileRepository;

    Logger log = LoggerFactory.getLogger((MethodHandles.lookup().lookupClass()));

    @ShellMethod("list all profiles")
    void listProfiles(){
        log.info("All profiles: {}", profileRepository.findAll());
    }
    @ShellMethod("list all profiles by ID")
    void listProfilesById(){
        long i = 1;
        while (true){
            try {
                Optional<Profile> maybeProfile = profileRepository.findById(i);
                if (maybeProfile.isEmpty()) break;
                log.info("Profile[["+i+"]]: {}", maybeProfile);
            } catch (Exception e){
                log.info("BREAKING FORCEFULLY AT i == "+i);
                break;
            }
            i++;
        }
        log.info("Ended when i == "+i);
    }



    /* OLD: EntityManagerCommands
    @PersistenceContext
    private EntityManager em;
    Logger log = LoggerFactory.getLogger((MethodHandles.lookup().lookupClass()));

    @ShellMethod("fillmore")
    void fillmore(){
        Profile fillmoreFat = em.find(Profile.class, 1L);
        System.out.println(fillmoreFat);
    }

    @Transactional
    @ShellMethod("show all unicorns")
    void showUnicorn(){
        var unicornWithProfile = em.find(Unicorn.class, 1L);
        log.info(unicornWithProfile.profile.photos.toString());//

        //var profile = em.find(Profile.class, 1L);
        //log.info(profile.toString());
    }*/
}

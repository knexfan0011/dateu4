package com.tutego.dateu4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping( "/api/profiles" )
public class ProfileRestController {
    @Autowired ProfileRepository profiles;

    @GetMapping
    public Iterable<Profile> profiles() {
        return profiles.findAll();
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<?> get(@PathVariable long id ) {
        Optional<Profile> maybeProfile = profiles.findById( id );
        return ResponseEntity.ok( maybeProfile );
    }

    @DeleteMapping( "/{id}" )
    public void delete( @PathVariable long id ) {
        // ResponseEntity.status(... ? NO_CONTENT : NOT_FOUND).build();
        profiles.deleteById( id );
    }
}
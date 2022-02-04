package com.tutego.dateu4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ImageBanner;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@ShellComponent
public class PhotoCommands {
    private final PhotoService photoService = new PhotoService();
    @Autowired
    JdbcTemplate jdbcTemplate;

    @ShellMethod("Show photo")
    void showPhoto(String imageName) {  // show-photo
        Optional<byte[]> maybeBytes = photoService.download(imageName);
        maybeBytes.ifPresent(bytes ->
                new ImageBanner(new ByteArrayResource(bytes))
                        .printBanner(new StandardEnvironment(), null, System.out));
    }

    @ShellMethod("Upload Photo")
    void uploadPhoto(String fileName) throws IOException {
        //String thumbName = photoService.upload(photoService.download(imageName).get());
        if (fileName.charAt(fileName.length()-1) == '*'){ // Upload all files ending in png or jpg in that directory
            String path = fileName.substring(0, fileName.length()-1);
            //TODO implement upload of all images in directory (or not)
            File[] listOfEntries = new File(path).listFiles();
            for (var entry:listOfEntries) {
                if (!entry.isFile()) continue;
                byte[] bytes = Files.readAllBytes(Paths.get(path + entry.getName()));
                photoService.upload(bytes);
            }

        } else {
            byte[] bytes = Files.readAllBytes(Paths.get(fileName));
            photoService.upload(bytes);
        }
    }

    @ShellMethod("Log Photo Information")
    void logAllPhotoInfo(){
        String sql = "SELECT * FROM PHOTO";
        Logger log = LoggerFactory.getLogger((MethodHandles.lookup().lookupClass()));

        var lst = jdbcTemplate.queryForList(sql);



        log.info( "All Photo info: {}", lst );
    }

}
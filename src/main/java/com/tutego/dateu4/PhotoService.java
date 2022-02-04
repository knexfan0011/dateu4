package com.tutego.dateu4;

import org.springframework.stereotype.Component;

import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PhotoService {
    //@Autowired
    private FileSystem fs = new FileSystem();
    private final String DEFAULT_PROFILE_PICTURE_NAME = "defaultUnicorn";
    //TODO: Maybe add "/img" as prefix ???

    private Optional<byte[]> getDefaultPic(){
        var defaultPic = download(DEFAULT_PROFILE_PICTURE_NAME);
        if (defaultPic.isEmpty()) System.err.println("Default Unicorn Picture not found");
        return defaultPic;
    }

    public String getLatestProfilePictureName(Profile profile){
        List<Photo> photos = profile.photos;
        if (photos == null || photos.size() == 0) return DEFAULT_PROFILE_PICTURE_NAME;
        Photo profilePic = null;
        for (Photo photo:photos) {
            if (!photo.isProfilePhoto) continue;
            profilePic = (profilePic == null || photo.created.compareTo(profilePic.created) > 0) ? photo : profilePic;
        }
        return (profilePic == null) ? DEFAULT_PROFILE_PICTURE_NAME : profilePic.name;
    }

    public Optional<byte[]> download(String imageName) {
        try {
            return Optional.of(fs.load(imageName + (imageName.endsWith(".jpg") ? "" : ".jpg")));
        } catch (UncheckedIOException e) {
            return Optional.empty();
        }
    }

    public String upload(byte[] imageBytes) {
        String imageName = UUID.randomUUID().toString();

        // First: store original image
        fs.store(imageName + ".jpg", imageBytes);

        // Second: store thumbnail
        AwtBicubicThumbnail thumbnail = new AwtBicubicThumbnail();
        byte[] thumbnailBytes = thumbnail.thumbnail(imageBytes);
        fs.store(imageName + "-thumb.jpg", thumbnailBytes);

        return imageName;
    }
}
package com.tutego.dateu4;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@RestController
public class PhotoRestController {

    /*static HashMap<String, String> imgNameToUrl = new HashMap<>();
    static {
        imgNameToUrl.put("schnee", "https://s3.envato.com/files/97d49363-c680-444f-85bb-fb68533d1c4d/inline_image_preview.jpg");
        imgNameToUrl.put("strand", "https://media.cntraveler.com/photos/59ef91dd8d4f736d51415c2e/master/w_2667,h_2000,c_limit/7MileBeach-2013-HiRes.jpg");
        imgNameToUrl.put("invalidImageUrl", "https://keinechteseiteichhoffedasiswirklichkeineechteseitedieichmirhiergradeausdenke-ichmeinewerwaeredennsodoofsichsoeinehirnrissigeurlzukaufenunddasauuchnuchmitshreipfailernnxd.org/dollesBilt.jpg");
    }
    @GetMapping(value = "/api/photo/random", produces = MediaType.IMAGE_JPEG_VALUE)
    //@Produces(MediaType.IMAGE_JPEG_VALUE)
    public byte[] photo() throws IOException, IOException {
        Resource resource = new UrlResource(
                "https://getwallpapers.com/wallpaper/full/1/1/7/264012.jpg"
        );
        InputStream inputStream = resource.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }

    @GetMapping(value = "/api/photo/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    //@Produces(MediaType.IMAGE_JPEG_VALUE)
    public byte[] givenPhoto(@PathVariable String name) throws IOException, IOException {
        Resource resource;
        try {
            resource = new UrlResource(imgNameToUrl.get(name));
        } catch (Exception e){

            return ; // name doesn't map to a URL
        }
        InputStream inputStream = resource.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }*/

    private final PhotoService photoService;

    public PhotoRestController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping(value = "/api/photo/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> photo(@PathVariable String imagename) {
        return ResponseEntity.of(photoService.download(imagename));
    }

}
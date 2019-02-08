package io.powersurfers.app.contoller;

import io.powersurfers.app.services.flickr.model.PhotoSet;
import io.powersurfers.app.services.flickr.service.FlickrService;
import io.powersurfers.app.services.flickr.service.FlickrServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api")
public class PhotosController {

    String apiKey = "8e4686f0cad0111d3c7d566d6ab69fec";
    String sharedSecret = "48a63849326bd950";

    @Autowired
    FlickrService flickrService;

    @GetMapping("/getPhotosByTag")
    public PhotoSet getPhotosListByTag(@RequestParam String tag) throws IOException {
        return flickrService.getPhotoSetByTag(tag);
        //return flickrService.getPhotoSetByPhotoSetIdAndUserId("72157674388093532","144522605@N06");
    }

    @GetMapping("/getPhotosById")
    public PhotoSet getPhotosListByPhotoSetIdAndUserId(@RequestParam String photoSetId, @RequestParam String userId) throws IOException {
        return flickrService.getPhotoSetByPhotoSetIdAndUserId(photoSetId, userId);
    }

    @GetMapping("/getPhotosByIdAndTag")
    public PhotoSet getPhotosListByPhotoSetIdAndUserIdAndTag(@RequestParam String photoSetId, @RequestParam String userId, @RequestParam String tag) throws IOException {
        return flickrService.getPhotoSetByPhotoSetIdAndUserIdAndTag(photoSetId, userId, tag);
    }

    @GetMapping("/test")
    public Object test(){
        return flickrService.getPhotoSourceById("30622823845");
    }


}

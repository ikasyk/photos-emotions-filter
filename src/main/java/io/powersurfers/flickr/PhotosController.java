package io.powersurfers.flickr;

import io.powersurfers.flickr.response.PhotoIdent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value="/api")
public class PhotosController {
    @GetMapping("/getPhotos")
    @ResponseBody
    public List<PhotoIdent> getPhotosList() {
        // TODO Retrieve photos from Flickr API using flickr.photosets.getPhotos and flickr.photos.getSizes methods
        return Collections.emptyList();
    }


}

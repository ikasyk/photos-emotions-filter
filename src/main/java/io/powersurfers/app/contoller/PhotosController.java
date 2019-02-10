package io.powersurfers.app.contoller;

import io.powersurfers.app.model.PhotoSet;
import io.powersurfers.app.service.PhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class PhotosController {
    private PhotosService photosService;

    @Autowired
    PhotosController(PhotosService photosService) {
        this.photosService = photosService;
    }

    @GetMapping("/getPhotosByTag")
    public PhotoSet getPhotosListByTag(@RequestParam String tag) {
        return photosService.getPhotoSetByTag(tag);
    }

    @GetMapping("/getPhotosById")
    public PhotoSet getPhotosListByPhotoSetIdAndUserId(@RequestParam String photoSetId, @RequestParam String userId) {
        return photosService.getPhotoSetByPhotoSetIdAndUserId(photoSetId, userId);
    }
}

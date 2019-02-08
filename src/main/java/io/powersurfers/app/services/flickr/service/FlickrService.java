package io.powersurfers.app.services.flickr.service;

import io.powersurfers.app.services.flickr.model.PhotoSet;
import io.powersurfers.app.services.flickr.model.Sources;

import java.util.List;

public interface FlickrService {
    PhotoSet getPhotoSetByPhotoSetIdAndUserId(String photoSetId, String userId);

    PhotoSet getPhotoSetByTag(String tag);

    PhotoSet getPhotoSetByPhotoSetIdAndUserIdAndTag(String photoSetId, String userId, String tag);

    List<Sources> getPhotoSourceById(String photoId);

}

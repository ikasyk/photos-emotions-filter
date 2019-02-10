package io.powersurfers.app.service;

import io.powersurfers.app.model.PhotoSet;

public interface PhotosService {
    PhotoSet getPhotoSetByPhotoSetIdAndUserId(String photoSetId, String userId);

    PhotoSet getPhotoSetByTag(String tag);

    PhotoSet mergePhotoSets(PhotoSet first, PhotoSet second);
}

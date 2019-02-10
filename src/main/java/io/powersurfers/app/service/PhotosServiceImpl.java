package io.powersurfers.app.service;

import io.powersurfers.app.model.Photo;
import io.powersurfers.app.model.PhotoSet;
import io.powersurfers.app.model.Source;
import io.powersurfers.app.model.faceplusplus.FppDetectApiResponse;
import io.powersurfers.app.model.flickr.FlickrPhotoSizesResponse;
import io.powersurfers.app.model.flickr.FlickrPhotosetResponse;
import io.powersurfers.app.model.flickr.FlickrResponseStatus;
import io.powersurfers.app.service.faceplusplus.FppHelper;
import io.powersurfers.app.service.faceplusplus.FppRequestException;
import io.powersurfers.app.service.faceplusplus.FppRestQueryEntityBuilder;
import io.powersurfers.app.service.flickr.FlickrHelper;
import io.powersurfers.app.service.flickr.FlickrRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhotosServiceImpl implements PhotosService {

    private Map<String, List<Source>> photoSourcesCache = new HashMap<>();
    private Map<String, List<String>> photoFacesCache = new HashMap<>();

    private FlickrHelper flickrHelper;
    private FppHelper fppHelper;

    @Autowired
    public PhotosServiceImpl(FlickrHelper flickrHelper, FppHelper fppHelper) {
        this.flickrHelper = flickrHelper;
        this.fppHelper = fppHelper;
    }

    @Override
    public PhotoSet getPhotoSetByPhotoSetIdAndUserId(String photoSetId, String userId) {
        String query = flickrHelper.getQueryUriById(photoSetId, userId);
        return getPhotosetByQuery(query);
    }

    @Override
    public PhotoSet getPhotoSetByTag(String tag) {
        String query = flickrHelper.getQueryUriByTag(tag);
        return getPhotosetByQuery(query);
    }

    private PhotoSet getPhotosetByQuery(String query) {
        FlickrPhotosetResponse photosetResponse = new RestTemplate()
                .getForObject(query, FlickrPhotosetResponse.class);

        if (photosetResponse.getStat() == FlickrResponseStatus.OK) {
            List<Photo> photos = photosetResponse.getPhotoset().getPhotos().stream()
                    .map(photo -> flickrHelper.wrapPhoto(photo))
                    .collect(Collectors.toList());

            PhotoSet photoSet = new PhotoSet();
            photoSet.setPhotos(photos);

            fillPhotoSources(photoSet);
            fillPhotoEmotions(photoSet);

            return photoSet;
        } else {
            throw new FlickrRequestException(photosetResponse.getMessage(), photosetResponse.getCode());
        }
    }

    private void fillPhotoSources(PhotoSet photoSet) {
        for (Photo photo : photoSet.getPhotos()) {
            String photoId = photo.getId();

            List<Source> sourcesInCache = photoSourcesCache.get(photoId);
            if (sourcesInCache != null) {
                photo.setSources(sourcesInCache);
                continue;
            }

            String query = flickrHelper.getSizesQueryUri(photoId);
            FlickrPhotoSizesResponse photoSizesResponse = new RestTemplate()
                    .getForObject(query, FlickrPhotoSizesResponse.class);

            if (photoSizesResponse.getStat() == FlickrResponseStatus.OK) {
                List<Source> sources = photoSizesResponse.getSources().getSources().stream()
                        .map(source -> flickrHelper.wrapSource(source))
                        .collect(Collectors.toList());

                photo.setSources(sources);

                photoSourcesCache.put(photoId, sources);
            } else {
                throw new FlickrRequestException(photoSizesResponse.getMessage(), photoSizesResponse.getCode());
            }
        }
    }

    private void fillPhotoEmotions(PhotoSet photoSet) {
        for (Photo photo : photoSet.getPhotos()) {
            String photoId = photo.getId();

            List<String> facesInCache = photoFacesCache.get(photoId);
            if (facesInCache != null) {
                photo.setFaces(facesInCache);
                continue;
            }
            String imageUrl = flickrHelper.findAppropriatePhotoUrl(photo);

            MultiValueMap<String, String> requestParams = fppHelper.getQueryByImageUrl(imageUrl);

            FppDetectApiResponse faceDetectResponse = new RestTemplate()
                    .postForObject(FppRestQueryEntityBuilder.QUERY_URL, requestParams, FppDetectApiResponse.class);

            if (faceDetectResponse.getErrorMessage() == null) {
                List<String> faces = fppHelper.getEmotionsFromResponse(faceDetectResponse);
                photo.setFaces(faces);

                photoFacesCache.put(photoId, faces);
            } else {
                throw new FppRequestException(faceDetectResponse.getErrorMessage());
            }
        }
    }

    @Override
    public PhotoSet mergePhotoSets(PhotoSet first, PhotoSet second) {
        PhotoSet result = new PhotoSet();

        Map<String, Photo> photosByIds = new HashMap<>();

        fppHelper.fillPhotosIdents(first, photosByIds);
        fppHelper.fillPhotosIdents(second, photosByIds);

        result.setPhotos(new ArrayList<>(photosByIds.values()));

        return result;
    }
}

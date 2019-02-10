package io.powersurfers.app.service.flickr;

import io.powersurfers.app.model.Photo;
import io.powersurfers.app.model.Source;
import io.powersurfers.app.model.flickr.FlickrPhotoSizesResponse;
import io.powersurfers.app.model.flickr.FlickrPhotosetResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("flickrHelper")
public class FlickrHelper {
    private static final String FLICKR_PHPTOSETS_GETPHOTOS_METHOD = "flickr.photosets.getPhotos";
    private static final String FLICKR_PHPTOSETS_SEARCH_METHOD = "flickr.photos.search";
    private static final String FLICKR_PHOTOS_SIZES_METHOD = "flickr.photos.getSizes";

    private static final int FLICKR_APPROPRIATE_PHOTO_SIZE = 300;

    private static final String FLICKR_PHOTO_SQUARE_SIZE = "Square";

    private static final String FLICKR_PHOTO_LARGE_SQUARE_SIZE = "Large Square";

    @Value("${flickr.app.apiKey}")
    private String apiKey;

    public String getQueryUriById(String photosetId, String userId) {
        FlickrRestQueryUriBuilder restQuery = new FlickrRestQueryUriBuilder(apiKey);

        restQuery.setMethod(FLICKR_PHPTOSETS_GETPHOTOS_METHOD);

        restQuery.setAlbumId(photosetId);
        restQuery.setUserId(userId);

        return restQuery.build();
    }

    public String getQueryUriByTag(String tag) {
        FlickrRestQueryUriBuilder restQuery = new FlickrRestQueryUriBuilder(apiKey);

        restQuery.setMethod(FLICKR_PHPTOSETS_SEARCH_METHOD);

        restQuery.addTag(tag);

        return restQuery.build();
    }

    public String getSizesQueryUri(String photoId) {
        FlickrRestQueryUriBuilder restQuery = new FlickrRestQueryUriBuilder(apiKey);

        restQuery.setMethod(FLICKR_PHOTOS_SIZES_METHOD);

        restQuery.setPhotoId(photoId);

        return restQuery.build();
    }

    public Photo wrapPhoto(FlickrPhotosetResponse.FlickrPhoto flickrPhoto) {
        Photo photo = new Photo();
        photo.setId(flickrPhoto.getId());
        photo.setTitle(flickrPhoto.getTitle());
        return photo;
    }

    public Source wrapSource(FlickrPhotoSizesResponse.FlickrSource flickrSource) {
        Source source = new Source();
        source.setLabel(flickrSource.getLabel());
        source.setWidth(flickrSource.getWidth());
        source.setHeight(flickrSource.getHeight());
        source.setSource(flickrSource.getUrl());
        return source;
    }

    public String findAppropriatePhotoUrl(Photo photo) {
        List<Source> sources = photo.getSources();

        for (Source source : sources) {
            if (source.getWidth() >= FLICKR_APPROPRIATE_PHOTO_SIZE
                    && source.getHeight() >= FLICKR_APPROPRIATE_PHOTO_SIZE
                    && !FLICKR_PHOTO_SQUARE_SIZE.equals(source.getLabel())
                    && !FLICKR_PHOTO_LARGE_SQUARE_SIZE.equals(source.getLabel())) {
                return source.getSource();
            }
        }
        if (sources.size() > 0) {
            return sources.get(0).getSource();
        }
        return null;
    }
}

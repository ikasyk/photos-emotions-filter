package io.powersurfers.app.service.flickr;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.Set;

public class FlickrRestQueryUriBuilder {
    private static final String QUERY_URL = "https://www.flickr.com/services/rest";

    private static final String QUERY_PARAM_METHOD = "method";
    private static final String QUERY_PARAM_API_KEY = "api_key";
    private static final String QUERY_PARAM_PHOTOSET_ID = "photoset_id";
    private static final String QUERY_PARAM_USER_ID = "user_id";
    private static final String QUERY_PARAM_PHOTO_ID = "photo_id";
    private static final String QUERY_PARAM_FORMAT = "format";
    private static final String QUERY_PARAM_NO_JSON_CALLBACK = "nojsoncallback";
    private static final String QUERY_PARAM_TAGS = "tags";

    private static final String QUERY_DEFAULT_FORMAT = "json";

    private String apiKey;
    private String method;
    private String albumId;
    private String userId;
    private String photoId;
    private Set<String> tags;

    public FlickrRestQueryUriBuilder(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void addTag(String tag) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        tags.add(tag);
    }

    @Override
    public String toString() {
        return build();
    }

    public String build() {
        UriComponentsBuilder restUriComponents = UriComponentsBuilder.fromHttpUrl(QUERY_URL);

        restUriComponents.queryParam(QUERY_PARAM_METHOD, method);
        restUriComponents.queryParam(QUERY_PARAM_API_KEY, apiKey);

        if (albumId != null) {
            restUriComponents.queryParam(QUERY_PARAM_PHOTOSET_ID, albumId);
        }

        if (userId != null) {
            restUriComponents.queryParam(QUERY_PARAM_USER_ID, userId);
        }

        if (photoId != null) {
            restUriComponents.queryParam(QUERY_PARAM_PHOTO_ID, photoId);
        }

        restUriComponents.queryParam(QUERY_PARAM_FORMAT, QUERY_DEFAULT_FORMAT);

        if ("json".equals(QUERY_DEFAULT_FORMAT)) {
            restUriComponents.queryParam(QUERY_PARAM_NO_JSON_CALLBACK, "1");
        }

        if (tags != null && !tags.isEmpty()) {
            String tagsList = String.join(",", tags);
            restUriComponents.queryParam(QUERY_PARAM_TAGS, tagsList);
        }

        return restUriComponents.toUriString();
    }
}

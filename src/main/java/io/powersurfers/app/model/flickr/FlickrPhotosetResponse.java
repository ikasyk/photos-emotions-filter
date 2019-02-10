package io.powersurfers.app.model.flickr;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class FlickrPhotosetResponse extends FlickrResponse {
    @JsonAlias({"photoset", "photos"})
    private FlickrPhotoset photoset;

    @Data
    @ToString
    static public class FlickrPhotoset implements Serializable {
        private String id;
        private String primary;

        @JsonProperty("photo")
        private List<FlickrPhoto> photos;

        private Integer page;

        @JsonProperty("per_page")
        private Integer photosPerPage;

        private Integer pages;
        private String title;

    }

    @Data
    @ToString
    static public class FlickrPhoto implements Serializable {
        private String id;
        private String secret;
        private String server;
        private Integer farm;
        private String title;
        private String isprimary;
        private Integer ispublic;
        private Integer isfriend;
        private Integer isfamily;
    }

}

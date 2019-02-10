package io.powersurfers.app.model.flickr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FlickrPhotoSizesResponse extends FlickrResponse {
    @JsonProperty("sizes")
    private FlickrSources sources;

    @Data
    public static class FlickrSources {
        @JsonProperty("size")
        private List<FlickrSource> sources;
    }

    @Data
    public static class FlickrSource {
        private String label;
        private Integer width;
        private Integer height;

        @JsonProperty("source")
        private String url;
    }
}

package io.powersurfers.app.services.flickr.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PhotoSet {
    @JsonProperty("photo")
    Set<Photo> photos;

    public PhotoSet(){
        photos = new HashSet<>();
    }
}

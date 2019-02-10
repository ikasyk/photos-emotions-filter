package io.powersurfers.app.model.flickr;

import lombok.Data;

import java.io.Serializable;

@Data
public class FlickrResponse implements Serializable {
    private FlickrResponseStatus stat;
    private Integer code;
    private String message;
}

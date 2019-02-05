package io.powersurfers.flickr.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class PhotoIdent implements Serializable {
    private final String sourceUrl;
    private final Integer flickId;
    private final String label;
    private final Integer width;
    private final Integer height;
}

package io.powersurfers.app.model.flickr;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum FlickrResponseStatus {
    OK,
    FAIL;

    @JsonCreator
    public static FlickrResponseStatus fromStringValue(String value) {
        if ("ok".equals(value)) {
            return OK;
        } else if ("fail".equals(value)) {
            return FAIL;
        }

        return null;
    }
}

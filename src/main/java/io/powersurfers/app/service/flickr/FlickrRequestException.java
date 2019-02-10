package io.powersurfers.app.service.flickr;

public class FlickrRequestException extends RuntimeException {
    private int code;

    public FlickrRequestException(String message, int code) {
        super(message);

        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

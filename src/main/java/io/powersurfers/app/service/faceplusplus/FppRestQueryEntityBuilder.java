package io.powersurfers.app.service.faceplusplus;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class FppRestQueryEntityBuilder {
    public static final String QUERY_URL = "https://api-us.faceplusplus.com/facepp/v3/detect";

    private static final String QUERY_PARAM_API_KEY = "api_key";
    private static final String QUERY_PARAM_SECRET_KEY = "api_secret";
    private static final String QUERY_PARAM_IMAGE_URL = "image_url";
    private static final String QUERY_PARAM_RETURN_ATTRS = "return_attributes";

    private static final String QUERY_EMOTION_ATTR = "emotion";

    private String apiKey;
    private String apiSecret;
    private String imageUrl;

    public FppRestQueryEntityBuilder(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MultiValueMap<String, String> build() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add(QUERY_PARAM_API_KEY, apiKey);
        params.add(QUERY_PARAM_SECRET_KEY, apiSecret);
        params.add(QUERY_PARAM_IMAGE_URL, imageUrl);
        params.add(QUERY_PARAM_RETURN_ATTRS, QUERY_EMOTION_ATTR);

        return params;
    }
}
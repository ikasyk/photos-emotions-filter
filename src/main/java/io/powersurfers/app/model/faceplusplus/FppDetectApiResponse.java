package io.powersurfers.app.model.faceplusplus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class FppDetectApiResponse {
    private List<FppFace> faces;

    @JsonProperty("error_message")
    private String errorMessage;

    @Data
    public static class FppFace {
        private FaceAttributes attributes;
    }

    @Data
    public static class FaceAttributes {
        private HashMap<String, Double> emotion;
    }
}

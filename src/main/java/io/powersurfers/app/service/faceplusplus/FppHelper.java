package io.powersurfers.app.service.faceplusplus;

import io.powersurfers.app.model.Photo;
import io.powersurfers.app.model.PhotoSet;
import io.powersurfers.app.model.faceplusplus.FppDetectApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("fppHelper")
public class FppHelper {
    @Value("${facepluplus.app.apiKey}")
    private String apiKey;

    @Value("${facepluplus.app.apiSecret}")
    private String apiSecret;

    public MultiValueMap<String, String> getQueryByImageUrl(String url) {
        FppRestQueryEntityBuilder query = new FppRestQueryEntityBuilder(apiKey, apiSecret);

        query.setImageUrl(url);

        return query.build();
    }

    public List<String> getEmotionsFromResponse(FppDetectApiResponse response) {
        List<String> results = new ArrayList<>();

        for (FppDetectApiResponse.FppFace face : response.getFaces()) {
            Map.Entry<String, Double> probableEmotion = null;

            if (face.getAttributes() == null) {
                continue;
            }

            for (Map.Entry<String, Double> emotionEntry : face.getAttributes().getEmotion().entrySet()) {
                if (probableEmotion == null) {
                    probableEmotion = emotionEntry;
                } else {
                    if (probableEmotion.getValue() < emotionEntry.getValue()) {
                        probableEmotion = emotionEntry;
                    }
                }
            }

            if (probableEmotion == null) {
                throw new FppRequestException("Emotions array is empty");
            }

            results.add(probableEmotion.getKey());
        }

        return results;
    }

    public void fillPhotosIdents(PhotoSet photoSet, Map<String, Photo> result) {
        for (Photo photo : photoSet.getPhotos()) {
            result.put(photo.getId(), photo);
        }
    }
}

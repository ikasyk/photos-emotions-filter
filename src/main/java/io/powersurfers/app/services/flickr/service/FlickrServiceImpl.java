package io.powersurfers.app.services.flickr.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.powersurfers.app.services.flickr.model.Photo;
import io.powersurfers.app.services.flickr.model.PhotoSet;
import io.powersurfers.app.services.flickr.model.Sources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class FlickrServiceImpl implements FlickrService {
    @Value("${flickr.app.apikey}")
    private String API_KEY = "8e4686f0cad0111d3c7d566d6ab69fec";

    public PhotoSet getPhotoSetByPhotoSetIdAndUserIdAndTag(String photoSetId, String userId, String tag) {

        PhotoSet photoSet = new PhotoSet();
        System.out.println(photoSet);
        photoSet.getPhotos().addAll(getPhotoSetByPhotoSetIdAndUserId(photoSetId, userId).getPhotos());
        photoSet.getPhotos().addAll(getPhotoSetByTag(tag).getPhotos());

        return photoSet;
    }

    @Override
    public PhotoSet getPhotoSetByPhotoSetIdAndUserId(String photoSetId, String userId) {
        JsonNode jsonPhotoSet = new RestTemplate().
                getForObject("https://www.flickr.com/services/rest/" +
                                "?method=flickr.photosets.getPhotos" +
                                "&api_key=" + API_KEY +
                                "&photoset_id=" + photoSetId +
                                "&user_id=" + userId +
                                "&format=json" +
                                "&nojsoncallback=1"
                        , JsonNode.class);
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            PhotoSet photoSet = mapper.treeToValue(jsonPhotoSet.get("photoset"), PhotoSet.class);
            System.out.println("OK");
            return photoSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PhotoSet();
    }

    @Override
    public PhotoSet getPhotoSetByTag(String tag) {
        JsonNode jsonPhotoSet = new RestTemplate().
                getForObject("https://www.flickr.com/services/rest/" +
                                "?method=flickr.photos.search" +
                                "&api_key=" + API_KEY +
                                "&tags=" + tag +
                                "&format=json" +
                                "&nojsoncallback=1"
                        , JsonNode.class);
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            PhotoSet photoSet = mapper.treeToValue(jsonPhotoSet.get("photos"), PhotoSet.class);
            return photoSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PhotoSet();
    }

    @Override
    public List<Sources> getPhotoSourceById(String photoId) {
        // https://www.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=8e4686f0cad0111d3c7d566d6ab69fec&photo_id=30622823845
        JsonNode jsonSources = new RestTemplate().
                getForObject("https://www.flickr.com/services/rest/" +
                                "?method=flickr.photos.getSizes" +
                                "&api_key=" + API_KEY +
                                "&photo_id=" + photoId +
                                "&format=json" +
                                "&nojsoncallback=1"
                        , JsonNode.class);



        // Тут ошибка - нудно преобразить jsonSources в List Sources что-бы потом уже забрать те которые хочеться розмеры
        try {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            ObjectReader reader = mapper.readerFor(new TypeReference<List<Sources>>() {
            });
            List<Sources> list = reader.readValue(jsonSources.get("sizes").get("size"));
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Hello - " + jsonSources.get("sizes").get("size"));
        return null;

    }
}

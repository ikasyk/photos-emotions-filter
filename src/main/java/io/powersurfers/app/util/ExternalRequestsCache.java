package io.powersurfers.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.powersurfers.app.model.Source;
import io.powersurfers.app.model.util.ExternalRequestStore;
import io.powersurfers.app.repository.ExternalRequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExternalRequestsCache {
    private static final String SOURCES_KEY = "sources";
    private static final String FACES_KEY = "faces";

    private ExternalRequestsRepository requestsRepository;

    @Autowired
    public ExternalRequestsCache(ExternalRequestsRepository requestsRepository) {
        this.requestsRepository = requestsRepository;
    }

    private long lastSourcesUpdateCount = 0;
    private long lastFacesUpdateCount = 0;

    private Map<String, List<Source>> photoSourcesCache = new HashMap<>();
    private Map<String, List<String>> photoFacesCache = new HashMap<>();

    public void cacheSources(String id, List<Source> sources) {
        photoSourcesCache.put(id, sources);
    }
    public List<Source> retrieveSources(String id) {
        return photoSourcesCache.get(id);
    }

    public void cacheFaces(String id, List<String> faces) {
        photoFacesCache.put(id, faces);
    }
    public List<String> retrieveFaces(String id) {
        return photoFacesCache.get(id);
    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void load() {
        try {
            ExternalRequestStore sourcesData = requestsRepository.findExternalRequestStoresByKey(SOURCES_KEY);

            if (sourcesData != null && sourcesData.getCache() != null) {
                photoSourcesCache = new ObjectMapper().readValue(sourcesData.getCache(), Map.class);
            }

            ExternalRequestStore facesData = requestsRepository.findExternalRequestStoresByKey(FACES_KEY);

            if (facesData != null && facesData.getCache() != null) {
                photoFacesCache = new ObjectMapper().readValue(facesData.getCache(), Map.class);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            if (lastSourcesUpdateCount < photoSourcesCache.size()) {
                saveSpecificCache(SOURCES_KEY, photoSourcesCache);
                lastSourcesUpdateCount = photoSourcesCache.size();
            }

            if (lastFacesUpdateCount < photoFacesCache.size()) {
                saveSpecificCache(FACES_KEY, photoFacesCache);
                lastFacesUpdateCount = photoFacesCache.size();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void saveSpecificCache(String key, Map cache) throws JsonProcessingException {
        ObjectMapper sourcesMapper = new ObjectMapper();

        ExternalRequestStore dbStore = requestsRepository.findExternalRequestStoresByKey(key);

        if (dbStore == null) {
            dbStore = new ExternalRequestStore();
            dbStore.setKey(key);
        }
        dbStore.setCache(sourcesMapper.writeValueAsString(cache));
        requestsRepository.save(dbStore);
    }
}

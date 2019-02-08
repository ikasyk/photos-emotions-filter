package io.powersurfers.app.services.flickr.model;

import lombok.Data;

import java.util.List;

/**
 * Photo class
 * @author michaelrudyy
 */
@Data
public class Photo {
    String id;
    String title;
    // список розмеров и ссилок на них
    List<Sources> sources;
    List<Face> faces;
}

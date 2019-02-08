package io.powersurfers.app.services.flickr.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sources {
    private String label; // название розмера например Original или Medium 800
    private int width;
    private int height;
    private String source;
}

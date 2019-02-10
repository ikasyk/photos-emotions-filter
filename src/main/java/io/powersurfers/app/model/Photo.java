package io.powersurfers.app.model;

import lombok.Data;

import java.util.List;

/**
 * Photo class
 * @author michaelrudyy
 */
@Data
public class Photo {
    private String id;
    private String title;
    private List<Source> sources;
    private List<String> faces;
}

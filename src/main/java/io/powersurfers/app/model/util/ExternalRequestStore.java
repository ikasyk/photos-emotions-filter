package io.powersurfers.app.model.util;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ExternalRequestStore {
    @Id
    private String id;

    private String key;

    private String cache;
}

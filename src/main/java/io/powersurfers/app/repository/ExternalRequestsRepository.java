package io.powersurfers.app.repository;

import io.powersurfers.app.model.util.ExternalRequestStore;
import org.springframework.data.repository.*;

public interface ExternalRequestsRepository extends CrudRepository<ExternalRequestStore, Long> {
    ExternalRequestStore findExternalRequestStoresByKey(String key);
}

package fundata.repository;

import fundata.document.MetaData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by huang on 17-4-19.
 */
public interface MetaDataRepository extends MongoRepository<MetaData, Long> {
    MetaData findByDatasetId(Long datasetId);
}

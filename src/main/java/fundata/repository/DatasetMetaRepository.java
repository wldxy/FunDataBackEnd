package fundata.repository;

import fundata.document.DatasetMeta;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by huang on 17-4-19.
 */
public interface DatasetMetaRepository extends MongoRepository<DatasetMeta, Long> {
    DatasetMeta findByDatasetId(Long datasetId);
}

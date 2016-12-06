package fundata.repository;

import fundata.model.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ocean on 16-12-1.
 */
public interface DatasetRepository extends JpaRepository<Dataset, Long>, JpaSpecificationExecutor<Dataset> {
    @Query("select u from Dataset u where u.name = ?1")
    Dataset findByDatasetName(String datasetName);

}
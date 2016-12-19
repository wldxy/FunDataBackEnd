package fundata.repository;

import fundata.model.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-12-1.
 */
public interface DatasetRepository extends JpaRepository<Dataset, Long>, JpaSpecificationExecutor<Dataset> {
    @Query("select u from Dataset u where u.name = ?1")
    Dataset findByDatasetName(String datasetName);

    @Query("select u from Dataset u where u.name like ?1")
    Set<Dataset> findLikeName(String name);

}
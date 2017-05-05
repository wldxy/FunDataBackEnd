package fundata.repository;

import fundata.model.Dataer;
import fundata.model.DataerDataset;
import fundata.model.DataerDatasetId;
import fundata.model.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huang on 17-4-21.
 */
@Repository
public interface DataerDatasetRepository extends JpaRepository<DataerDataset, DataerDatasetId>, JpaSpecificationExecutor<DataerDataset> {
    @Query("select c from DataerDataset c where c.dataer = ?1")
    List<DataerDataset> findAllDatasetsByUser(Dataer dataer);

    @Query("select c from DataerDataset c")
    List<DataerDataset> findAllDatasets();

    @Query("select c from DataerDataset c where c.role = 0 and c.dataset.id = ?1")
    DataerDataset findDatasetByDatasetId(Long datasetId);

    @Query("select c from DataerDataset c where c.dataset in ?1 and c.role = 0")
    List<DataerDataset> findDatasetOwner(Object[] datasets);

}

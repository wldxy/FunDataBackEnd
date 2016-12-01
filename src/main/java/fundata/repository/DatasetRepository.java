package fundata.repository;

import fundata.model.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by ocean on 16-12-1.
 */
public interface DatasetRepository extends JpaRepository<Dataset, Long>, JpaSpecificationExecutor<Dataset> {

}
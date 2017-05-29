package fundata.repository;

import fundata.model.DataFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ocean on 16-12-7.
 */
@Repository
public interface DataFileRepository extends JpaRepository<DataFile, Long> {
    DataFile findById(Long id);
}

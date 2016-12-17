package fundata.service;

import fundata.model.DataFile;
import fundata.model.Dataer;
import fundata.model.Dataset;
import fundata.model.DatasetTitle;
import fundata.repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-12-1.
 */
public interface DatasetService {
    Set<Dataset> findByUserName(String username);

    void addDataset(String username, String datasetname, String desc);

    Set<DatasetTitle> getDatasetTitle(String datasetName);

    void addDatasetTitle(String datasetName, DatasetTitle datasetTitle);

    List<Dataer> getContribute(String datasetName);

    List<DataFile> getDatasetFile(String datasetName);

    Dataset findByDatasetName(String datasetName);

    void save(Dataset dataset);
}

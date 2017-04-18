package fundata.service;

import fundata.model.DataFile;
import fundata.model.Dataer;
import fundata.model.Dataset;
import fundata.model.DatasetTitle;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ocean on 16-12-1.
 */
public interface DatasetService {
    Set<Dataset> findByUserName(String username);

    Set<Dataset> findLikeName(String username);

    void addDataset(Long id, String datasetName, String dsDesc, String formatDesc, Map<String, Integer> columns);

    Set<DatasetTitle> getDatasetTitle(String datasetName);

    void addDatasetTitle(String datasetName, DatasetTitle datasetTitle);

    List<Dataer> getContribute(String datasetName);

    List<DataFile> getDatasetFile(String datasetName);

    Dataset findByDatasetName(String datasetName);

    void save(Dataset dataset);

    void combineDataset(String datasetName) throws FileNotFoundException;

//    List<Dataset> findAll();
}

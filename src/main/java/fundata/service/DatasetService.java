package fundata.service;

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
    public Set<Dataset> findByUserName(String username);

    public void addDataset(String username, String datasetname);

    public Set<DatasetTitle> getDatasetTitle(String datasetName);

    public void addDatasetTitle(String datasetName, DatasetTitle datasetTitle);
}

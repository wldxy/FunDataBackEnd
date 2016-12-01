package fundata.service;

import fundata.model.Dataset;
import fundata.repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ocean on 16-12-1.
 */
public interface DatasetService {
    public List<Dataset> findByUserName(String username);

    public void addDataset(String username, String datasetname);
}

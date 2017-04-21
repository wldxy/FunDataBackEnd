package fundata.service;

import fundata.model.*;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-12-1.
 */
public interface DatasetService {
    PagedListHolder<DataerDataset> getUserDatasetsByPage(Long userId, int curPage);

    List<DataerDataset> getAllUserDatasets(Long userId);

    Set<Dataset> findLikeName(String username);

    void addDataset(Long id, String datasetName, String dsDesc, String formatDesc, String columnsJSONString);

    Set<DatasetTitle> getDatasetTitle(String datasetName);

    void addDatasetTitle(String datasetName, DatasetTitle datasetTitle);

    List<Dataer> getContribute(String datasetName);

    List<DataFile> getDatasetFile(String datasetName);

    Dataset findByDatasetName(String datasetName);

    void save(Dataset dataset);

    void combineDataset(String datasetName) throws FileNotFoundException;

//    List<Dataset> findAll();
}

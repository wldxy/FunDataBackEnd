package fundata.service;

import fundata.model.*;
import org.springframework.beans.support.PagedListHolder;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-12-1.
 */
public interface DatasetService {
    List<DataerDataset> getAllUserDatasets(Long userId);

    Object[] assembleDatasetInfo(PagedListHolder<DataerDataset> result);

    PagedListHolder<DataerDataset> getAllDatasetsByPage(int curPage);

    PagedListHolder<DataerDataset> getUserDatasetsByPage(Long userId, int curPage);

    void createNewDataset(Long id, String datasetName, String dsDesc, String formatDesc, String columnsJSONString, String coverUrl);

    Set<Dataset> findLikeName(String username);

    Set<DatasetTitle> getDatasetTitle(String datasetName);

    void addDatasetTitle(String datasetName, DatasetTitle datasetTitle);

    List<Dataer> getContribute(String datasetName);

    List<DataFile> getDatasetFile(String datasetName);

    Dataset findByDatasetName(String datasetName);

    void save(Dataset dataset);

    void combineDataset(String datasetName) throws FileNotFoundException;

//    List<Dataset> findAll();
}

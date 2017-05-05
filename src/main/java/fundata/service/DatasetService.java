package fundata.service;

import fundata.model.*;
import fundata.viewmodel.DatasetContent;
import fundata.viewmodel.DatasetDetail;
import org.springframework.beans.support.PagedListHolder;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-12-1.
 */
public interface DatasetService {
    List<DataerDataset> getAllUserDatasets(Long userId);

    List<Object> assembleDatasetInfos(PagedListHolder<DataerDataset> result);

    PagedListHolder<DataerDataset> getAllDatasetsByPage(int curPage);

    PagedListHolder<DataerDataset> getUserDatasetsByPage(Long userId, int curPage);

    void createNewDataset(Long id, String datasetName, String dsDesc, String formatDesc, String columnsJSONString, String coverUrl);

    DatasetDetail getDatasetDetail(Long datasetId);

    DatasetContent getDatasetContent(Long datasetId, Long dataerId);

    Set<Dataset> searchDatasets(String username);

    Set<DatasetTitle> getDatasetTitle(String datasetName);

    void addDatasetTitle(String datasetName, DatasetTitle datasetTitle);

    List<Dataer> getContribute(String datasetName);

    List<DataFile> getDatasetFile(String datasetName);

    Dataset findByDatasetName(String datasetName);

    void save(Dataset dataset);

    void combineDataset(Long datasetId) throws FileNotFoundException;

}

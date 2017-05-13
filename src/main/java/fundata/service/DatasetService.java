package fundata.service;

import fundata.document.Field;
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

    Set<Dataset> searchDatasets(String userName);

    List<Dataer> getContribute(Long datasetId);

    List<DataFile> getDatasetFiles(Long datasetId);

    Dataset findByDatasetName(String datasetName);

    Field[] getDatasetColumns(Long datasetId);

    void save(Dataset dataset);

    void combineDataset(Long datasetId) throws FileNotFoundException;

}

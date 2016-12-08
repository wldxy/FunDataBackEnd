package fundata.viewmodel;

import fundata.model.Dataset;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-12-1.
 */
public class MyDataset {
    public List<DatasetInfo> getDataset() {
        return dataset;
    }

    public void setDataset(List<DatasetInfo> dataset) {
        this.dataset = dataset;
    }

    List<DatasetInfo> dataset = new LinkedList<>();

    public MyDataset(Iterable<Dataset> datasets) {
        for (Dataset item: datasets) {
            DatasetInfo datasetInfo = new DatasetInfo(item.getName());
            this.dataset.add(datasetInfo);
        }
    }
}

class DatasetInfo {
    DatasetInfo(String datasetname) {
        this.datasetname = datasetname;
    }

    private String datasetname;

    public String getDatasetname() {
        return datasetname;
    }

    public void setDatasetname(String datasetname) {
        this.datasetname = datasetname;
    }
}

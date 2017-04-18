package fundata.viewmodel;

import fundata.model.Dataset;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ocean on 16-12-1.
 */
public class MyDataset {
    public List<DatasetInfo> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<DatasetInfo> datasets) {
        this.datasets = datasets;
    }

    List<DatasetInfo> datasets = new LinkedList<>();

    public MyDataset(Iterable<Dataset> datasets) {
        for (Dataset item: datasets) {
            DatasetInfo datasetInfo = new DatasetInfo(item.getName(), item.getDsDescription(), item.getPullRequests().size());
            this.datasets.add(datasetInfo);
        }
    }
}

class DatasetInfo {
    DatasetInfo(String datasetname, String description, Integer size) {
        this.description = description;
        this.datasetname = datasetname;
        this.size = size;
    }

    private String datasetname;

    private String description;

    private Integer size;

    public String getDatasetname() {
        return datasetname;
    }

    public void setDatasetname(String datasetname) {
        this.datasetname = datasetname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}

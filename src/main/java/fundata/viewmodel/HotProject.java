package fundata.viewmodel;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ocean on 16-12-12.
 */
public class HotProject {
    private List<HotInfo> dataset = new ArrayList<>();

    public HotProject() {

    }

    public void addInfo(String datasetName, String userName, int type) {
        dataset.add(new HotInfo(datasetName, userName, 1));
    }

    public List<HotInfo> getDataset() {
        return dataset;
    }

    public void setDataset(List<HotInfo> dataset) {
        this.dataset = dataset;
    }
}

class HotInfo {
    public HotInfo(String datasetName, String userName, int type) {
        this.datasetName = datasetName;
        this.datasetName = userName;
        this.type = type;
    }

    private String datasetName;

    private String userName;

    private Integer type;

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

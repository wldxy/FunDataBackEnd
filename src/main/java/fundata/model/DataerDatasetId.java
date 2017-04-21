package fundata.model;

import java.io.Serializable;

/**
 * Created by huang on 17-4-21.
 */
public class DataerDatasetId implements Serializable {

    private Long dataerId;
    private Long datasetId;

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public Long getDataerId() {

        return dataerId;
    }

    public void setDataerId(Long dataerId) {
        this.dataerId = dataerId;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataerDatasetId that = (DataerDatasetId) o;

        if (!dataerId.equals(that.dataerId)) return false;
        return datasetId.equals(that.datasetId);
    }

    @Override
    public int hashCode() {
        int result = dataerId.hashCode();
        result = 31 * result + datasetId.hashCode();
        return result;
    }
}

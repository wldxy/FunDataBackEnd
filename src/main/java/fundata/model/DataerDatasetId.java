package fundata.model;

import java.io.Serializable;

/**
 * Created by huang on 17-4-21.
 */
public class DataerDatasetId implements Serializable {

    private Long dataer;
    private Long dataset;

    public Long getDataset() {
        return dataset;
    }

    public void setDataset(Long dataset) {
        this.dataset = dataset;
    }

    public Long getDataer() {

        return dataer;
    }

    public void setDataer(Long dataer) {
        this.dataer = dataer;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataerDatasetId that = (DataerDatasetId) o;

        if (!dataer.equals(that.dataer)) return false;
        return dataset.equals(that.dataset);
    }

    @Override
    public int hashCode() {
        int result = dataer.hashCode();
        result = 31 * result + dataset.hashCode();
        return result;
    }
}

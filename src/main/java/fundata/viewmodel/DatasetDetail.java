package fundata.viewmodel;

import fundata.document.Field;

/**
 * Created by huang on 17-5-4.
 */
public class DatasetDetail {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DatasetInfo getDatasetInfo() {
        return datasetInfo;
    }

    public void setDatasetInfo(DatasetInfo datasetInfo) {
        this.datasetInfo = datasetInfo;
    }

    public Field[] getColumns() {
        return columns;
    }

    public void setColumns(Field[] columns) {
        this.columns = columns;
    }

    private DatasetInfo datasetInfo;
    private Field[] columns;
}

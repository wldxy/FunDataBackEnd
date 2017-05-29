package fundata.viewmodel;

import fundata.document.Table;

/**
 * Created by huang on 17-5-4.
 */
public class DatasetDetail {

    private String url;
    private DatasetInfo datasetInfo;
    private Table[] tables;

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

    public Table[] getTables() {
        return tables;
    }

    public void setTables(Table[] tables) {
        this.tables = tables;
    }
}

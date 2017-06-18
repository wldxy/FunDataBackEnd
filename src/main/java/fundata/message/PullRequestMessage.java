package fundata.message;

/**
 * Created by huang on 17-5-20.
 */
public class PullRequestMessage {
    private Long id;
    private Long datasetId;
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public PullRequestMessage() {}

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public PullRequestMessage(Long id, Long datasetId, String fileUrl, String tableName) {
        this.id = id;
        this.datasetId = datasetId;
        this.fileUrl = fileUrl;
        this.tableName = tableName;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    private String fileUrl;
}

package fundata.message;

/**
 * Created by huang on 17-5-20.
 */
public class PullRequestMessage {
    private Long id;
    private Long datasetId;

    public PullRequestMessage() {}

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public PullRequestMessage(Long id, Long datasetId, String fileUrl) {
        this.id = id;
        this.datasetId = datasetId;
        this.fileUrl = fileUrl;
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

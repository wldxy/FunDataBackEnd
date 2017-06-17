package fundata.message;

/**
 * Created by huang on 17-5-20.
 */
public class MergeRequestMessage {
    private String newUrl;
    private String mainUrl;
    private Long datasetId;

    public Long getPullRequestId() {
        return pullRequestId;
    }

    public void setPullRequestId(Long pullRequestId) {
        this.pullRequestId = pullRequestId;
    }

    private Long pullRequestId;

    public MergeRequestMessage() {}

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public MergeRequestMessage(String newUrl, String mainUrl, Long datasetId, Long pullRequestId) {
        this.newUrl = newUrl;
        this.mainUrl = mainUrl;
        this.datasetId = datasetId;
        this.pullRequestId = pullRequestId;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }
}

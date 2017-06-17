package fundata.message;

/**
 * Created by huang on 17-5-20.
 */
public class MergeRequestMessage {
    private String newUrl;
    private String mainUrl;
    private Long datasetId;

    public MergeRequestMessage() {}

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public MergeRequestMessage(String newUrl, String mainUrl, Long datasetId) {
        this.newUrl = newUrl;
        this.datasetId = datasetId;
        this.mainUrl = mainUrl;
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

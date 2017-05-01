package fundata.viewmodel;

import java.util.Date;

/**
 * Created by huang on 17-4-30.
 */
public class PullRequestInfo {
    private Long id;
    private String dataerName;
    private String dataerUrl;
    private String pullDescription;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataerName() {
        return dataerName;
    }

    public void setDataerName(String dataerName) {
        this.dataerName = dataerName;
    }

    public String getDataerUrl() {
        return dataerUrl;
    }

    public void setDataerUrl(String dataerUrl) {
        this.dataerUrl = dataerUrl;
    }

    public String getPullDescription() {
        return pullDescription;
    }

    public void setPullDescription(String pullDescription) {
        this.pullDescription = pullDescription;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}

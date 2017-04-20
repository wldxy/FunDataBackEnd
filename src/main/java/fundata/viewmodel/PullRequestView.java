package fundata.viewmodel;

import fundata.model.PullRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-12-6.
 */
@Component
public class PullRequestView {
//    @Autowired
//    private QiniuService qiniuService;

    public PullRequestView(int count) {
        this.count = count;
    }

    public PullRequestView(Set<PullRequest> pullRequests) {
        this.count = 0;
        for (PullRequest p : pullRequests) {
//            this.pullrequest.add(new RequestInfo(p,
//                    qiniuService.createDownloadUrl(p.getDataFile())));
            this.count += 1;
        }
    }

    public void add(PullRequest pullRequest, String url) {
        this.count++;
        this.pullrequest.add(new RequestInfo(pullRequest, url));
    }

    private int count;

    private List<RequestInfo> pullrequest = new ArrayList<>();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RequestInfo> getPullrequest() {
        return pullrequest;
    }

    public void setPullrequest(List<RequestInfo> pullrequest) {
        this.pullrequest = pullrequest;
    }
}

class RequestInfo {

    public RequestInfo(PullRequest pullRequest, String url) {
        this.id = pullRequest.getId();
        this.updatetime = pullRequest.getUpdateTime();
        this.type = pullRequest.getStatus();
        this.description = pullRequest.getDescription();
        this.username = pullRequest.getDataer().getName();
        this.url = url;
//
//        if (pullRequest.getDataFile() == null) {
//            this.url = "";
//        } else {
//            DataFile dataFile = pullRequest.getDataFile();
//            this.url = qiniuService.createDownloadUrl(dataFile);
//        }
    }

    private String username;

    private Date updatetime;

    private Long id;

    private Integer type;

    private String description;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
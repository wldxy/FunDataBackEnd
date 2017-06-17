package fundata.document;

import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.*;

/**
 * Created by ocean on 16-12-6.
 */
@Document
public class PullRequestStatistics {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Field("pullrequest_id")
    private Long pullRequestId;

    public Map<String, Boolean> getResult() {
        return result;
    }

    public void setResult(Map<String, Boolean> result) {
        this.result = result;
    }

    @Field("result")
    private Map<String, Boolean> result;

    public Long getPullRequestId() {
        return pullRequestId;
    }

    public void setPullRequestId(Long pullRequestId) {
        this.pullRequestId = pullRequestId;
    }

}
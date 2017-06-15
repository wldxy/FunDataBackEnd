package fundata.document;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.*;

/**
 * Created by ocean on 16-12-6.
 */
@Document
public class PullRequestStatistics {
    @Id
    private Long pullRequestId;

    public Map<String, String> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, String> limits) {
        this.limits = limits;
    }

    private Map<String, String> limits;

    public Long getPullRequestId() {
        return pullRequestId;
    }

    public void setPullRequestId(Long pullRequestId) {
        this.pullRequestId = pullRequestId;
    }

}
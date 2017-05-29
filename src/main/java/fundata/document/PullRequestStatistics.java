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

    private List<Map<String, Double>> limits;

    public Long getPullRequestId() {
        return pullRequestId;
    }

    public void setPullRequestId(Long pullRequestId) {
        this.pullRequestId = pullRequestId;
    }

    public List<Map<String, Double>> getLimits() {
        return limits;
    }

    public void setLimits(List<Map<String, Double>> limits) {
        this.limits = limits;
    }
}
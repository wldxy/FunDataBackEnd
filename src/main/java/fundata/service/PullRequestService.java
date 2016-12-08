package fundata.service;

import fundata.model.PullRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-12-7.
 */
public interface PullRequestService {
    Set<PullRequest> findByDatasetName(String datasetName);

    PullRequest newPullRequest(String dataerName, String datasetName);

    boolean setPullRequest(Long id, Integer status);
}

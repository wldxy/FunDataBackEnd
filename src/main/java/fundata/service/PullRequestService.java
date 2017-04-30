package fundata.service;

import fundata.model.DataerDataset;
import fundata.model.PullRequest;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-12-7.
 */
public interface PullRequestService {
    List<Object> assemblePullRequestInfo(PagedListHolder<PullRequest> result);

    Set<PullRequest> getDatasetAllPullRequestsByPage(String datasetName);

    PullRequest newPullRequest(String dataerName, String datasetName);

    boolean setPullRequest(Long id, Integer status);

    List<PullRequest> getAllUserPullRequests(Long userId);

    PagedListHolder<PullRequest> getUserPullRequestsByPage(Long userId, short curPage);

    PagedListHolder<PullRequest> findLatestPullRequest(Long userId, int curPage);

    void save(PullRequest pullRequest);
}

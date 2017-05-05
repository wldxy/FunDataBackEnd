package fundata.service;

import fundata.model.DataFile;
import fundata.model.PullRequest;
import org.springframework.beans.support.PagedListHolder;

import java.util.List;

/**
 * Created by ocean on 16-12-7.
 */
public interface PullRequestService {
    List<Object> assemblePullRequestInfo(PagedListHolder<PullRequest> result);

    PagedListHolder<PullRequest> getDatasetPullRequestsByPage(Long datasetId, short curPage);

    List<PullRequest> getDatasetPullRequests(Long datasetId);

    PagedListHolder<PullRequest> getUserPullRequestsByPage(Long userId, short curPage);

    List<PullRequest> getUserPullRequests(Long datasetId);

    boolean createPullRequest(Long dataerId, Long datasetId, String description, String fileUrl);

    boolean setPullRequestStatus(Long id, short status);

    PagedListHolder<PullRequest> findLatestPullRequest(Long userId, int curPage);

    void save(PullRequest pullRequest);
}

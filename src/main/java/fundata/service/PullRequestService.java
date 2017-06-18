package fundata.service;

import fundata.model.PullRequest;
import fundata.viewmodel.PullRequestDetail;
import org.springframework.beans.support.PagedListHolder;

import java.util.List;

/**
 * Created by ocean on 16-12-7.
 */
public interface PullRequestService {
    List<Object> assemblePullRequestInfos(PagedListHolder<PullRequest> result);

    PagedListHolder<PullRequest> getDatasetPullRequestsByPage(Long datasetId, short curPage);

    List<PullRequest> getDatasetPullRequests(Long datasetId);

    PagedListHolder<PullRequest> getUserPullRequestsByPage(Long userId, short curPage);

    PullRequestDetail getPullRequestDetail(Long pullRequestId);

    List<PullRequest> getUserPullRequests(Long datasetId);

    boolean createPullRequest(Long dataerId, Long datasetId, String description, String fileUrl, String tableName);

    boolean mergePullRequest(Long pullRequestId, String tag);

    boolean rejectPullRequest(Long pullRequestId);

    PagedListHolder<PullRequest> findLatestPullRequest(Long userId, int curPage);

}

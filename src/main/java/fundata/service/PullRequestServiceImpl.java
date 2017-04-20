package fundata.service;

import fundata.configure.Constants;
import fundata.model.Dataer;
import fundata.model.Dataset;
import fundata.model.PullRequest;
import fundata.repository.DataFileRepository;
import fundata.repository.DataerRepository;
import fundata.repository.DatasetRepository;
import fundata.repository.PullRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ocean on 16-12-7.
 */
@Service
public class PullRequestServiceImpl implements PullRequestService {
    @Autowired
    PullRequestRepository pullRequestRepository;

    @Autowired
    DatasetRepository datasetRepository;

    @Autowired
    DataerRepository dataerRepository;

    @Autowired
    DataFileRepository dataFileRepository;

    @Autowired
    DatasetService datasetService;

    @Override
    public Set<PullRequest> findByDatasetName(String datasetName) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);
        if (dataset != null) {
            return dataset.getPullRequests();
        } else {
            return null;
        }
    }

    @Override
    public PullRequest newPullRequest(String dataerName, String datasetName) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);
        Dataer dataer = dataerRepository.findByUserName(dataerName);

        if (dataer == null || dataset == null) {
            return null;
        }

        PullRequest pullRequest = new PullRequest();
        pullRequest.setDataer(dataer);
        pullRequest.setDataset(dataset);
        pullRequest.setStatus(-1);
        pullRequest.setUpdateTime(new Date());
        pullRequestRepository.save(pullRequest);

        return pullRequest;
    }

    @Override
    public boolean setPullRequest(Long id, Integer status) {
        PullRequest pullRequest = pullRequestRepository.findOne(id);
        if (pullRequest != null) {
            pullRequest.setStatus(status);
            pullRequestRepository.save(pullRequest);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<PullRequest> getAllUserPullRequests(Long userId) {
        List<PullRequest> pullRequests = new ArrayList<>();
        List<Dataset> datasets = datasetService.getAllUserDatasets(userId);
        for (Dataset dataset : datasets) {
            pullRequests.addAll(dataset.getPullRequests());
        }
        return pullRequests;
    }

    @Override
    public PagedListHolder<PullRequest> getUserPullRequestsByPage(Long userId, int curPage) {
        List<PullRequest> pullRequests = getAllUserPullRequests(userId);
        PagedListHolder<PullRequest> pullRequestPage = new PagedListHolder<PullRequest>(pullRequests);
        pullRequestPage.setSort(new MutableSortDefinition("name", true, true));
        pullRequestPage.setPage(curPage);
        pullRequestPage.setPageSize(Constants.pageSize);
        return pullRequestPage;
    }

    @Override
    public PagedListHolder<PullRequest> findLatestPullRequest(Long userId, int curPage) {
//        Specification<PullRequest> pullRequestSpecification = new Specification<PullRequest>() {
//            @Override
//            public Predicate toPredicate(Root<PullRequest> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Join<PullRequest, Dataset> requestDatasetJoin = root.join("dataset", JoinType.INNER);
////                Set<Dataset> datasets = datasetService.findByUserName(dataerName);
////                ArrayList<Long> arrayList;
////                List<Predicate> predicateList = new ArrayList<>();
////                predicateList.add()
//                Join<Dataset, Dataer> datasetDataerJoin = criteriaQuery.from(Dataset.class).join("datasets", JoinType.INNER);
//
//                Predicate p1 = criteriaBuilder.equal(requestDatasetJoin.get("name"), dataerName);
//                Predicate p2 = criteriaBuilder.
//            }
//        };
        List<PullRequest> pullRequests = getAllUserPullRequests(userId);
        PagedListHolder<PullRequest> pullRequestPage = new PagedListHolder<PullRequest>(pullRequests);
        pullRequestPage.setSort(new MutableSortDefinition("updateTime", true, false));
        pullRequestPage.setPage(curPage);
        pullRequestPage.setPageSize(Constants.pageSize);
        return pullRequestPage;
    }

    @Override
    public void save(PullRequest pullRequest) {
        pullRequestRepository.save(pullRequest);
    }

}

package fundata.service;

import fundata.configure.Constants;
import fundata.message.MergeRequestMessage;
import fundata.message.Producer;
import fundata.message.PullRequestMessage;
import fundata.model.*;
import fundata.repository.*;
import fundata.document.PullRequestStatistics;
import fundata.viewmodel.PullRequestDetail;
import fundata.viewmodel.PullRequestInfo;
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
    private PullRequestRepository pullRequestRepository;

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private DataerRepository dataerRepository;

    @Autowired
    private DataFileRepository dataFileRepository;

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private PullRequestDetailRepository pullRequestDetailRepository;

    @Autowired
    private DatasetService datasetService;

    @Autowired
    private Producer producer;

    private PullRequestInfo assemblePullRequestInfo(PullRequest pullRequest) {
        PullRequestInfo pullRequestInfo = new PullRequestInfo();
        Dataer dataer = pullRequest.getDataer();
        pullRequestInfo.setUpdateTime(pullRequest.getUpdateTime());
        pullRequestInfo.setPullDescription(pullRequest.getDescription());
        pullRequestInfo.setDataerName(dataer.getName());
        pullRequestInfo.setDataerUrl(dataer.getHead_href());
        pullRequestInfo.setId(pullRequest.getId());
        return pullRequestInfo;
    }

    @Override
    public PullRequestDetail getPullRequestDetail(Long pullRequestId) {
        PullRequestDetail pullRequestDetail = new PullRequestDetail();
        PullRequestStatistics pullRequestStatistics = pullRequestDetailRepository.findByPullRequestId(pullRequestId);
        PullRequest pullRequest = pullRequestRepository.findOne(pullRequestId);
        pullRequestDetail.setColumns(datasetService.getDatasetTables(pullRequest.getDataset().getId()));
        if (pullRequestStatistics != null) {
            pullRequestDetail.setLimits(pullRequestStatistics.getResult());
        }
        pullRequestDetail.setUrl(qiniuService.createDownloadUrl(pullRequest.getDataFile().getUrl()));
        pullRequestDetail.setId(pullRequestId);
        return pullRequestDetail;
    }

    @Override
    public List<Object> assemblePullRequestInfos(PagedListHolder<PullRequest> result) {
        return Arrays.asList(result.getPageList().stream().map(this::assemblePullRequestInfo).toArray());
    }

    @Override
    public List<PullRequest> getUserPullRequests(Long userId) {
        List<DataerDataset> datasets = dataerRepository.findOne(userId).getDatasets();
        List<PullRequest> pullRequests = new ArrayList<>();
        for (DataerDataset dataset : datasets) {
            pullRequests.addAll(getDatasetPullRequests(dataset.getDataset().getId()));
        }
        return pullRequests;
    }

    @Override
    public PagedListHolder<PullRequest> getUserPullRequestsByPage(Long userId, short curPage) {
        List<PullRequest> pullRequests = getUserPullRequests(userId);
        PagedListHolder<PullRequest> pullRequestPage = new PagedListHolder<PullRequest>(pullRequests);
        pullRequestPage.setSort(new MutableSortDefinition("dataer.name", true, true));
        pullRequestPage.resort();
        pullRequestPage.setPage(curPage);
        pullRequestPage.setPageSize(Constants.pageSize);
        return pullRequestPage;
    }

    @Override
    public List<PullRequest> getDatasetPullRequests(Long datasetId) {
        return datasetRepository.findOne(datasetId).getPullRequests();
    }

    @Override
    public PagedListHolder<PullRequest> getDatasetPullRequestsByPage(Long datasetId, short curPage) {
        List<PullRequest> pullRequests = getDatasetPullRequests(datasetId);
        PagedListHolder<PullRequest> pullRequestPage = new PagedListHolder<PullRequest>(pullRequests);
        pullRequestPage.setSort(new MutableSortDefinition("dataer.name", true, true));
        pullRequestPage.resort();
        pullRequestPage.setPage(curPage);
        pullRequestPage.setPageSize(Constants.pageSize);
        return pullRequestPage;
    }

    @Override
    public boolean createPullRequest(Long dataerId, Long datasetId, String description, String fileUrl, String tableName) {
        Dataset dataset = datasetRepository.findOne(datasetId);
        Dataer dataer = dataerRepository.findOne(dataerId);

        if (dataer == null || dataset == null) {
            return false;
        }
        DataFile dataFile = new DataFile();
        dataFile.setCreateTime(new Date());
        dataFile.setUrl(fileUrl);
        dataFileRepository.save(dataFile);
        PullRequest pullRequest = new PullRequest();
        pullRequest.setDataer(dataer);
        pullRequest.setDataset(dataset);
        pullRequest.setUpdateTime(new Date());
        pullRequest.setDataFile(dataFile);
        pullRequest.setDescription(description);
        pullRequest.setTableName(tableName);
        pullRequestRepository.save(pullRequest);
        producer.send(new PullRequestMessage(pullRequest.getId(), datasetId, fileUrl, tableName));
        return true;
    }

    @Override
    public boolean mergePullRequest(Long pullRequestId, String tag) {
        PullRequest pullRequest = pullRequestRepository.findOne(pullRequestId);
        Dataset dataset = pullRequest.getDataset();
        if (pullRequest != null) {
            pullRequest.setStatus((short)0);
            pullRequest.setTag(tag);
            pullRequestRepository.save(pullRequest);
            String main_url = dataset.getFile() == null ? "" : dataset.getFile().getUrl();
            producer.send(new MergeRequestMessage(pullRequest.getDataFile().getUrl(), main_url, dataset.getId(), pullRequestId));
            if (main_url.equals("")) {
                dataset.setFile(pullRequest.getDataFile());
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean rejectPullRequest(Long pullRequestId) {
        PullRequest pullRequest = pullRequestRepository.findOne(pullRequestId);
        if (pullRequest != null) {
            pullRequest.setStatus((short)1);
            pullRequestRepository.save(pullRequest);
            return true;
        }
        else {
            return false;
        }
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
        List<PullRequest> pullRequests = getUserPullRequests(userId);
        PagedListHolder<PullRequest> pullRequestPage = new PagedListHolder<PullRequest>(pullRequests);
        pullRequestPage.setSort(new MutableSortDefinition("updateTime", true, false));
        pullRequestPage.setPage(curPage);
        pullRequestPage.setPageSize(Constants.pageSize);
        return pullRequestPage;
    }
}

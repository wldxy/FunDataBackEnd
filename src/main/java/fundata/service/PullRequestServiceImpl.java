package fundata.service;

import fundata.model.DataFile;
import fundata.model.Dataer;
import fundata.model.Dataset;
import fundata.model.PullRequest;
import fundata.repository.DataFileRepository;
import fundata.repository.DataerRepository;
import fundata.repository.DatasetRepository;
import fundata.repository.PullRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;
import javax.xml.crypto.Data;
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
        pullRequest.setUpdatetime(new Date());
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
    public Page<PullRequest> findLatestPullRequest(String dataerName, int page, int size) {
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
        List<PullRequest> pullRequests = new ArrayList<>();
        Set<Dataset> datasets = datasetService.findByUserName(dataerName);
        for (Dataset dataset : datasets) {
            for (PullRequest pullRequest : dataset.getPullRequests()) {
                pullRequests.add(pullRequest);
            }
        }
//        pullRequests.sort(new Comparator<PullRequest>() {
//            @Override
//            public int compare(PullRequest o1, PullRequest o2) {
//                return (o1.getUpdatetime().compareTo(o2.getUpdatetime());
//            }
//        });
        Page<PullRequest> pullRequestPage = new PageImpl<PullRequest>(pullRequests,
                new PageRequest(page, size, new Sort(Sort.Direction.DESC, new String("updatetime"))),
                pullRequests.size());
        return pullRequestPage;
    }

    @Override
    public void save(PullRequest pullRequest) {
        pullRequestRepository.save(pullRequest);
    }

}

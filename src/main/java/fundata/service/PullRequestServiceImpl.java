package fundata.service;

import fundata.model.Dataset;
import fundata.model.PullRequest;
import fundata.repository.DatasetRepository;
import fundata.repository.PullRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by ocean on 16-12-7.
 */
@Service
public class PullRequestServiceImpl implements PullRequestService {
    @Autowired
    PullRequestRepository pullRequestRepository;

    @Autowired
    DatasetRepository datasetRepository;

    @Override
    public Set<PullRequest> findByDatasetName(String datasetName) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);
        if (dataset != null) {
            return dataset.getPullRequests();
        } else {
            return null;
        }
    }
}

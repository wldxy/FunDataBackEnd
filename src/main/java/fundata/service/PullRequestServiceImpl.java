package fundata.service;

import fundata.model.DataFile;
import fundata.model.Dataer;
import fundata.model.Dataset;
import fundata.model.PullRequest;
import fundata.repository.DataFileRepository;
import fundata.repository.DataerRepository;
import fundata.repository.DatasetRepository;
import fundata.repository.PullRequestRepository;
import oracle.sql.TIMESTAMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    DataerRepository dataerRepository;

    @Autowired
    DataFileRepository dataFileRepository;

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

        PullRequest pullRequest = new PullRequest();
        pullRequest.setDataer(dataer);
        pullRequest.setDataset(dataset);
        pullRequest.setStatus(-1);
        pullRequest.setUpdatetime(new Date());

        DataFile dataFile = new DataFile();
        dataFile.setStatus(-1);
        dataFile.setCreateTime(new Date());
        dataFile.setUpdateTime(new Date());
        dataFileRepository.save(dataFile);
        pullRequest.setDataFile(dataFile);

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

}

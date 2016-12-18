package fundata.service;

import fundata.model.*;
import fundata.repository.DataerRepository;
import fundata.repository.DatasetRepository;
import fundata.repository.DatasetTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-12-1.
 */
@Service
public class DatasetServiceImpl implements DatasetService {
    @Autowired
    DatasetRepository datasetRepository;

    @Autowired
    DataerRepository dataerRepository;

    @Autowired
    DatasetTitleRepository datasetTitleRepository;

    @Override
    public Set<Dataset> findByUserName(String userName) {
//        List<Dataset> datasetList = datasetRepository.findAll(new Specification<Dataset>() {
//            @Override
//            public Predicate toPredicate(Root<Dataset> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Join<Dataset, Dataer> dataerJoin = root.join("dataers", JoinType.INNER);
//                return criteriaBuilder.equal(dataerJoin.get("name"), userName);
//            }
//        });
        Dataer dataer = dataerRepository.findByUserName(userName);

        return dataer.getDatasets();
    }

//    @Override
//    public List<Dataset> findAll(){
//        return  datasetRepository.findAll();
//
//    }

    @Override
    public void addDataset(String username, String datasetname, String desc) {
        Dataer dataer = dataerRepository.findByUserName(username);
        System.out.println(dataer.getEmail());
        Dataset dataset = new Dataset(datasetname);
        dataset.setDescription(desc);
        datasetRepository.save(dataset);
        dataer.getDatasets().add(dataset);
        dataerRepository.save(dataer);
        System.out.println(datasetname + " success");
    }

    @Override
    public Set<DatasetTitle> getDatasetTitle(String datasetName) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);
        Set<DatasetTitle> datasetTitleSet = dataset.getDatasetTitles();
        return datasetTitleSet;
    }

    @Override
    public void addDatasetTitle(String datasetName, DatasetTitle datasetTitle) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);
        dataset.getDatasetTitles().add(datasetTitle);
        datasetRepository.save(dataset);
    }

    @Override
    public List<Dataer> getContribute(String datasetNsame) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetNsame);
        Set<PullRequest> pullRequests = dataset.getPullRequests();
        List<Dataer> dataers = new ArrayList<>();
        for (PullRequest pullRequest : pullRequests) {
            dataers.add(pullRequest.getDataer());
        }
        return dataers;
    }

    @Override
    public List<DataFile> getDatasetFile(String datasetName) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);
        Set<DataFile> dataFiles = dataset.getFiles();
        List<DataFile> dataFileList = new ArrayList<DataFile>(dataFiles);
        return dataFileList;
    }

    @Override
    public Dataset findByDatasetName(String datasetName) {
        Dataset dataset = datasetRepository.findByDatasetName(datasetName);
        return dataset;
    }

    @Override
    public void save(Dataset dataset) {
        datasetRepository.save(dataset);
    }
}


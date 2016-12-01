package fundata.service;

import fundata.model.Dataer;
import fundata.model.Dataset;
import fundata.repository.DataerRepository;
import fundata.repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by ocean on 16-12-1.
 */
@Service
public class DatasetServiceImpl implements DatasetService {
    @Autowired
    DatasetRepository datasetRepository;

    @Autowired
    DataerRepository dataerRepository;

    @Override
    public List<Dataset> findByUserName(String userName) {
        List<Dataset> datasetList = datasetRepository.findAll(new Specification<Dataset>() {
            @Override
            public Predicate toPredicate(Root<Dataset> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Dataset, Dataer> dataerJoin = root.join("dataers", JoinType.INNER);
                return criteriaBuilder.equal(dataerJoin.get("name"), userName);
            }
        });
        return datasetList;
    }

    @Override
    public void addDataset(String username, String datasetname) {
        Dataer dataer = dataerRepository.findByUserName(username);
        System.out.println(dataer.getEmail());
        Dataset dataset = new Dataset(datasetname);
        datasetRepository.save(dataset);
        dataer.getDatasets().add(dataset);
        dataerRepository.save(dataer);
        System.out.println(datasetname + " success");
    }


}

package fundata.service;

import fundata.model.Competition;
import fundata.model.Dataer;
import fundata.repository.DataerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by stanforxc on 2016/12/5.
 */
@Service
public class DataerServiceImpl implements DataerService {

    @Autowired
    DataerRepository dataerRepository;

    @Transactional
    @Override
    public void save(Dataer dataer) {
        dataerRepository.save(dataer);
    }

    @Override
    public Dataer findById(Long id) {
        return dataerRepository.findById(id);
    }

    @Override
    public Dataer findByHostcompetition(Competition competition) {
        return dataerRepository.findByHostcompetition(competition);
    }

    @Override
    public Dataer findByDataerName(String dataerName) {
        return dataerRepository.findByUserName(dataerName);
    }


}

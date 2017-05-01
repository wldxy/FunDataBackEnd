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
    public boolean editUserInfo(Long id, String newName, String newPwd, String newUrl) {
        Dataer dataer = dataerRepository.findOne(id);
        if (newName != null) {
            dataer.setName(newName);
        }
        if (newPwd != null) {
            dataer.setPassword(newPwd);
        }
        Dataer d = dataerRepository.save(dataer);
        return d != null;
    }

    @Override
    public Dataer getUserById(Long id) {
        return dataerRepository.findOne(id);
    }

    @Override
    public Dataer findByEmail(String email) {
        return dataerRepository.findByUserEmail(email);
    }

    @Override
    public Dataer findByHostcompetition(Competition competition) {
        return dataerRepository.findByHostcompetition(competition);
    }

    @Override
    public Dataer findByDataerName(String dataerName) {
        return dataerRepository.findByUserName(dataerName);
    }

    @Override
    public Set<Dataer> findLikeName(String dataerName) {
        return dataerRepository.findLikeName(dataerName);
    }


}

package fundata.service;

import fundata.model.Accurate;
import fundata.model.Dataer;
import fundata.repository.AccurateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by stanforxc on 2016/12/13.
 */
@Service
public class AccurateServiveImpl implements AccurateService{
    @Autowired
    AccurateRepository accurateRepository;

    @Override
    public boolean save(Accurate accurate) {
        try {
            accurateRepository.save(accurate);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public Set<Accurate> findByDataer(Dataer dataer) {
        try {
            return accurateRepository.findByDataer(dataer);
        }catch (Exception e){
            return null;
        }
    }
}

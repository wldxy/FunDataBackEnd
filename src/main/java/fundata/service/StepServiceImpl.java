package fundata.service;

import fundata.model.Course;
import fundata.model.Step;
import fundata.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.GeneratedValue;
import java.util.Set;

/**
 * Created by stanforxc on 2016/12/5.
 */
@Service
public class StepServiceImpl implements StepService {
    @Autowired
    StepRepository stepRepository;

    @Override
    public void save(Step step) {
        stepRepository.save(step);
    }

    @Transactional
    @Override
    public void updateStepContent(String content, int stepid) {
        stepRepository.updateStep(content,stepid);
    }

    @Override
    public Set<Step> findByCourse(Course course) {
        return stepRepository.findByCourse(course);
    }

    @Override
    public Step findById(Long id) {
        return stepRepository.findOne(id);
    }




}

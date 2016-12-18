package fundata.service;

import fundata.model.Course;
import fundata.model.Step;

import java.util.Set;

/**
 * Created by stanforxc on 2016/12/5.
 */
public interface StepService {
    void save(Step step);
    void updateStepContent(String content,int stepid);
    Set<Step> findByCourse(Course course);
    Step findById(Long id);
}

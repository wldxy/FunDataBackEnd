package fundata.service;

import fundata.model.Course;
import fundata.model.Question;

import java.util.Set;

/**
 * Created by stanforxc on 2016/12/5.
 */
public interface QuestionService {
    void save(Question question);
    Question findById(Long id);
    Set<Question> findByCourse(Course course);
}

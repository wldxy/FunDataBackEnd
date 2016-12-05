package fundata.service;

import fundata.model.Course;
import fundata.model.Question;
import fundata.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by stanforxc on 2016/12/5.
 */
@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    QuestionRepository questionRepository;

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Set<Question> findByCourse(Course course) {
        return questionRepository.findByCourse(course);
    }
}

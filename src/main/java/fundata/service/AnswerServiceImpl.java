package fundata.service;

import fundata.model.Answer;
import fundata.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by stanforxc on 2016/12/5.
 */
@Service
public class AnswerServiceImpl implements AnswerService{
    @Autowired
    AnswerRepository answerRepository;
    @Override
    public void save(Answer answer) {
        answerRepository.save(answer);
    }
}

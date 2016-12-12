package fundata.service;

import fundata.model.CommentComp;
import fundata.repository.CommentCompRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by stanforxc on 2016/12/12.
 */
@Service
public class CommentCompServiceImpl implements CommentCompService {
    @Autowired
    CommentCompRepository commentCompRepository;


    @Override
    public void save(CommentComp commentComp) {
        commentCompRepository.save(commentComp);
    }
}

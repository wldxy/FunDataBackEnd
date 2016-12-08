package fundata.repository;

import fundata.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by stanforxc on 2016/12/5.
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long>{
}

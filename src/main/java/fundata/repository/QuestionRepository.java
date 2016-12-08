package fundata.repository;

import fundata.model.Course;
import fundata.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by stanforxc on 2016/12/5.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question,Long>{
    Question findById(Long id);
    Set<Question> findByCourse(Course course);
}

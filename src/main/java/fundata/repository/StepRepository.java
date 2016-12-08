package fundata.repository;

import fundata.model.Course;
import fundata.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by stanforxc on 2016/12/5.
 */
public interface StepRepository extends JpaRepository<Step,Long> {
    @Transactional
    @Modifying
    @Query("update Step s set s.content = ?1 where s.stepid = ?2")
    int updateStep(String content,int courseId);

    Set<Step> findByCourse(Course course);
}

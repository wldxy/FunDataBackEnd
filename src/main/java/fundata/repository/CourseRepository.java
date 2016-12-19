package fundata.repository;

import fundata.model.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by stanforxc on 2016/12/2.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    Set<Course> findByName(String name);

    Course findById(Long id);

    @Query("select c.step from Course c where c.id = ?1")
    int getStepNum(Long id);

    @Query("select u from Course u where u.name like ?1")
    Set<Course> findLikeName(String name);

    @Query("select c from Course c order by c.registerNum DESC")
    List<Course> findHotest(Pageable pagable);

    List<Course> findAll(Sort sort);

    @Transactional
    Long deleteById(Long id);

    @Transactional
    @Modifying
    @Query("update Course c set c.step = c.step +1 where c.id = ?1")
    int increaseStep(Long id);
}

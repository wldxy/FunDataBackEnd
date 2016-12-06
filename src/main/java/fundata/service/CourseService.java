package fundata.service;

import fundata.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Created by stanforxc on 2016/12/2.
 */
public interface CourseService {
    Set<Course> findByName(String course_name);
    Course findById(Long id);
    void save(Course course);
    void deleteCourseById(Long id);
    boolean increaseStep(Long id);
    int getStepNum(Long id);
    List<Course> findHotest(Pageable pageable);
    Page<Course> findAll(Pageable pageable);
}

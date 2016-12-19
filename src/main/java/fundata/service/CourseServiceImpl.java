package fundata.service;

import fundata.model.Course;
import fundata.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

/**
 * Created by stanforxc on 2016/12/2.
 */
@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    CourseRepository courseRepository;

    @Override
    public Set<Course> findByName(String course_name) {
        return courseRepository.findByName(course_name);
    }

    @Override
    public Set<Course> findLikeName(String course_name) {
        return courseRepository.findLikeName(course_name);
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id);
    }

    @Transactional
    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Transactional
    @Override
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean increaseStep(Long id) {
        if(findById(id) != null){
            courseRepository.increaseStep(id);
            return true;
        }
        return false;
    }

    @Override
    public int getStepNum(Long id) {
        return courseRepository.getStepNum(id);
    }

    @Override
    public List<Course> findHotest(Pageable pageable){
        return courseRepository.findHotest(pageable);
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }
}

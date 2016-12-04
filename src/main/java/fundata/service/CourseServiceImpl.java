package fundata.service;

import fundata.model.Course;
import fundata.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    @Transactional
    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public List<Course> findHotest(int pageNum,int size){
        return courseRepository.findHotest(new PageRequest(pageNum,size));
    }
}

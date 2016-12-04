package fundata.service;

import fundata.model.Course;

import java.util.List;
import java.util.Set;

/**
 * Created by stanforxc on 2016/12/2.
 */
public interface CourseService {
    public Set<Course> findByName(String course_name);
    public void save(Course course);
    public List<Course> findHotest(int pageNum,int size);
}

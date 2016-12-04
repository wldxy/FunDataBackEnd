package fundata.control;

import fundata.model.Course;
import fundata.service.CourseService;
import fundata.viewmodel.BCourse;
import fundata.viewmodel.TopClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import sun.net.httpserver.HttpServerImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by stanforxc on 2016/12/2.
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseServiceImpl;

    @ResponseBody
    @RequestMapping("/screen_hot_course")
    public List<Course> screen_hot_course(Model model) throws IOException{
        return courseServiceImpl.findHotest(0,5);
    }

    @ResponseBody
    @RequestMapping("/boutique_course/{page}/{size}")
    public Object boutique_course(@PathVariable int page,@PathVariable int size,Model model)throws IOException{
        BCourse bc = new BCourse();
        List<TopClass> topClasses = new ArrayList<TopClass>();
        List<Course> courses = courseServiceImpl.findHotest(page,size);
        for (int i = 0;i < courses.size();++i) {
            topClasses.add(new TopClass(courses.get(i).getId(),courses.get(i).getName(),i,courses.get(i).getRegisterNum(),courses.get(i).getTeacher()));
        }
        bc.setBoutique_course(topClasses);
        return bc;
    }

    @ResponseBody
    @RequestMapping("/Insert")
    public void insert(){
            Course course = new Course();
            course.setId(1L);
            course.setName("fds");
            course.setTeacher("fff");
            course.setRegisterNum(8);
            courseServiceImpl.save(course);
    }
}

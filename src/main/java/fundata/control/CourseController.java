package fundata.control;

import fundata.model.Course;
import fundata.service.CourseServiceImpl;
import fundata.service.QiniuServiceImpl;
import fundata.viewmodel.BCourse;
import fundata.viewmodel.TopClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * Created by stanforxc on 2016/12/2.
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseServiceImpl courseServiceImpl;

    @Autowired
    QiniuServiceImpl qiniuServiceImpl;

    @ResponseBody
    @RequestMapping("/screen_hot_course")
    public List<Course> screen_hot_course(Model model) throws IOException{
        return courseServiceImpl.findHotest(0,5);
    }

    @ResponseBody
    @RequestMapping("/boutique_course/{page}/{size}")
    public BCourse boutique_course(@PathVariable int page,@PathVariable int size,Model model)throws IOException{
        BCourse bc = new BCourse();
        List<TopClass> topClasses = new ArrayList<TopClass>();
        List<Course> courses = courseServiceImpl.findHotest(page,size);
        for (int i = 0;i < courses.size();++i) {
            topClasses.add(new TopClass(courses.get(i).getId(),courses.get(i).getName(),i,courses.get(i).getRegisterNum(),courses.get(i).getTeacher()));
        }
        bc.setBoutique_course(topClasses);
        return bc;
    }

    /*
    * 注册课程
    * */
    @ResponseBody
    @RequestMapping(value = "/add/{courseId}/{courseName}/{description}/{teacher}/{overview}",method = RequestMethod.GET)
    public boolean registerCourse(@PathVariable Long courseId, @PathVariable String courseName,@PathVariable String overview,@PathVariable String description,@PathVariable String teacher){
        try {
            courseServiceImpl.save(new Course(courseId,courseName,teacher,description,overview,0,"",0));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /*
    * 删除课程
    * */
    @ResponseBody
    @RequestMapping(value = "/delete/{courseId}",method = RequestMethod.GET)
    public boolean deleteCourse(@PathVariable Long courseId){
        try {
            courseServiceImpl.deleteCourseById(courseId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /*
    * 添加step*/
    @ResponseBody
    @RequestMapping(value = "/{courseId}/step{num}",method = RequestMethod.GET)
    public boolean addStep(@PathVariable Long courseId,@PathVariable int num){
       try {
           return true;
       }catch (Exception ex){
           return false;
       }
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

package fundata.control;

import fundata.model.*;
import fundata.repository.DataFileRepository;
import fundata.service.*;
import fundata.viewmodel.BCourse;
import fundata.viewmodel.TopClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    StepServiceImpl stepServiceImpl;

    @Autowired
    DataerServiceImpl dataerServiceImpl;

    @Autowired
    QuestionServiceImpl questionServiceImpl;

    @Autowired
    AnswerServiceImpl answerServiceImpl;


    @ResponseBody
    @RequestMapping("/screen_hot_course")
    public List<Course> screen_hot_course(Model model) throws IOException{
        return courseServiceImpl.findHotest(new PageRequest(0,5));
    }

    @ResponseBody
    @RequestMapping("/boutique_course/more/{pagenum}")
    public BCourse boutique_course(@PathVariable int pagenum/*,@PageableDefault(page = size = 1,sort = "registerNum",direction = Sort.Direction.DESC)Pageable pageable*/)throws IOException{
        BCourse bc = new BCourse();
        List<TopClass> topClasses = new ArrayList<TopClass>();
       List<Course> courses = courseServiceImpl.findHotest(new PageRequest(pagenum,8));
        for (int i = 0;i < courses.size();++i) {
            topClasses.add(new TopClass(courses.get(i).getId(),courses.get(i).getName(),i,courses.get(i).getRegisterNum(),courses.get(i).getHoster().getName()));
        }
        bc.setBoutique_course(topClasses);
        return bc;
    }

    /*
    * 注册课程
    * */
    @ResponseBody
    @RequestMapping(value = "/add/{courseId}/{courseName}/{description}/{teacher}/{overview}",method = RequestMethod.POST)
    public boolean registerCourse(@PathVariable String courseName,
                                  @PathVariable String description,
                                  @PathVariable String teacher){
        try {
            Dataer dataer = dataerServiceImpl.findByDataerName(teacher);
            courseServiceImpl.save(new Course(courseName, dataer, description,  0, "", 0));
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
    * 添加step
    * */
//    @ResponseBody
//    @RequestMapping(value = "/{courseId}/step{num}/{stepname}/{content}",method = RequestMethod.GET)
//    public boolean addStep(@PathVariable Long courseId,@PathVariable String num,@PathVariable String stepname,@PathVariable String content){
//       try {
//           int number = Integer.parseInt(num);
//           if(number > courseServiceImpl.getStepNum(courseId)){
//               /*设置step内容*/
//               Step step = new Step();
//               step.setStepname(stepname);
//               step.setContent(content);
//               int key = Integer.parseInt(courseId.toString()+ num);
//               step.setStepid(key);
//               /*找到对应course*/
//               Course course = courseServiceImpl.findById(courseId);
//               step.setCourse(course);
//               stepServiceImpl.save(step);
//               courseServiceImpl.increaseStep(courseId);
//           }else {
//               stepServiceImpl.updateStepContent(content,number);
//           }
//           return true;
//       }catch (Exception ex){
//           return false;
//       }
//    }

    /*添加问题*/
    @ResponseBody
    @RequestMapping("/question/{userid}/{courseId}/q{num}/{courseMame}/{content}")
    public boolean add_question(@PathVariable Long userid,@PathVariable Long courseId,@PathVariable String courseMame,@PathVariable String content,@PathVariable String num){
        try{
            String time = getCurrentTime();
            Course course = courseServiceImpl.findById(courseId);
            Dataer dataer = dataerServiceImpl.findById(userid);
            Question question = new Question();
            Long key = Long.parseLong(userid.toString()+ courseId.toString()+ num);
            question.setId(key);
            question.setDataer(dataer);
            question.setCourse(course);
            question.setUpdatetime(time);
            question.setCreatetime(time);
            question.setContent(content);
            question.setAnswered(false);
            questionServiceImpl.save(question);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    /*添加问题的回答*/
    @ResponseBody
    @RequestMapping("/answer/{userid}/{courseId}/q{numq}/a{numa}/{courseName}/{content}")
    public boolean add_answer(@PathVariable Long userid,@PathVariable Long courseId,@PathVariable String numq,@PathVariable String numa,@PathVariable String courseName,@PathVariable String content){
        try{
            Long question_id = Long.parseLong(userid.toString()+ courseId.toString()+ numq);
            Question question = questionServiceImpl.findById(question_id);
            if (!question.getAnswered()) {
                question.setAnswered(true);
                questionServiceImpl.save(question);
            }
            Answer answer = new Answer();
            answer.setId(Long.parseLong(userid.toString() + courseId.toString() + numq + numa));
            answer.setCreatetime(getCurrentTime());
            answer.setContent(content);
            answer.setQuestion(question);
            Set<Answer> answerSet = question.getAnswers();
            answerSet.add(answer);
            question.setAnswers(answerSet);
            answerServiceImpl.save(answer);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    private String getCurrentTime(){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    /*
    * 返回courseDetail
    * */
    @ResponseBody
    @RequestMapping("/{courseId}/{courseName}/detail")
    public Map course_detail(@PathVariable Long courseId,@PathVariable String courseName){
        Course course = courseServiceImpl.findById(courseId);
        Map wrapper = new HashMap();
        Map stepmap = StepMap(courseId);
        Map questionmap = question(courseId);
        wrapper.put("course_id",courseId);
        wrapper.put("course_name",courseName);
        wrapper.put("course_overview",course.getOverview());
        wrapper.put("course_steps",stepmap);
        wrapper.put("course_qa",questionmap);
        Map total = new HashMap();
        total.put("course_detail",wrapper);
        return total;
    }

    private Map question(Long courseId){
        HashMap course_qas = new HashMap();
        List<HashMap> answeredList = new ArrayList<>();
        List<HashMap> unansweredList = new ArrayList<>();
        Course course = courseServiceImpl.findById(courseId);
        Set<Question> questionSet = questionServiceImpl.findByCourse(course);
        Iterator<Question> iter = questionSet.iterator();
        Integer qCount = 0;
        while (iter.hasNext()){
            Question q = iter.next();
            qCount++;
            HashMap total = new HashMap();
            HashMap questionMap = new HashMap();
            questionMap.put("question_id",q.getId());
            questionMap.put("question_owner_name",q.getDataer().getName());
            questionMap.put("question_owner_id",q.getDataer().getId());
            questionMap.put("question_createtime",q.getCreatetime());
            questionMap.put("question_updatetime",q.getUpdatetime());
            questionMap.put("question_content",q.getContent());
            total.put("question",questionMap);
            if(q.getAnswered()){
                Set<Answer> answerSet = q.getAnswers();
                Iterator<Answer> iterAnswer = answerSet.iterator();
                Integer aCount = 0;
                List<HashMap> question_answer = new ArrayList<>();
                while (iterAnswer.hasNext()){
                    aCount++;
                    Answer a = iterAnswer.next();
                    HashMap answerMap = new HashMap();
                    answerMap.put("answer_id",a.getId());
                    answerMap.put("answer_owner_name",a.getQuestion().getDataer().getName());
                    answerMap.put("answer_owner_id",a.getQuestion().getDataer().getId());
                    answerMap.put("answer_time",a.getCreatetime());
                    answerMap.put("answer_content",a.getContent());
                    question_answer.add(answerMap);
                }
                total.put("answer",question_answer);
                answeredList.add(total);

            }else {
                unansweredList.add(total);
            }
        }
        course_qas.put("answered",answeredList);
        course_qas.put("unanswered",unansweredList);
        /*获得answered*/
        return course_qas;
    }

    private Map StepMap(Long courseId){
        /*获得课程信息*/
        Course course = courseServiceImpl.findById(courseId);

        /*获得所有step*/
        HashMap course_steps = new HashMap();
        List<HashMap>  stepList = new ArrayList<HashMap>();
        Set<Step> stepSet = stepServiceImpl.findByCourse(course);
        Iterator<Step> iter = stepSet.iterator();
        while (iter.hasNext()){
            Step t = iter.next();
            HashMap st = new HashMap();
            st.put("step_header",t.getStepname());
            st.put("step_content",t.getContent());
            st.put("step_pic_url",t.getPictureUrl());
            stepList.add(st);
        }
        course_steps.put("course_steps",stepList);
        return course_steps;
    }


    @ResponseBody
    @RequestMapping("/Insert")
    public String insert(){
        Dataer dataer = new Dataer();
        dataer.setName("hongjiayong");
        dataer.setEmail("3@163.com");
        dataer.setPassword("fgfdgs");
        dataerServiceImpl.save(dataer);
        return "true";
    }


    // 添加课程
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map addCourse(@RequestParam String username, @RequestParam String coursename, @RequestParam String des){
        try{
            Dataer dataer = dataerServiceImpl.findByDataerName(username);
            Set<Course> courses = dataer.getHostCourses();
            Course course = new Course(coursename, dataer, des, 0, "", 0);
            courseServiceImpl.save(course);
            courses.add(course);
            dataer.setHostCourses(courses);

            Map map = new HashMap<>();
            map.put("id", course.getId());
            map.put("name", course.getName());
            map.put("hoster", course.getHoster().getName());
            map.put("description", course.getDescription());

            return map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 删除课程
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public boolean deleteCourse(@RequestParam String username, @RequestParam Long id){
        try{
            courseServiceImpl.deleteCourseById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // 学生注册课程
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public boolean registerCourse(@RequestParam String username,
                                  @RequestParam Long id,
                                  @RequestParam Integer type){
        try{

            Dataer dataer = dataerServiceImpl.findByDataerName(username);
            Course course = courseServiceImpl.findById(id);

            if (type == 1){
                course.getDataers().add(dataer);
            }
            else if (type == 0) {
                course.getDataers().remove(dataer);
            }

            courseServiceImpl.save(course);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // 课程详细页面
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Map courseDetail(@RequestParam(name = "username") String name,
                            @RequestParam(name = "id") Long id){
        try{
            Course course = courseServiceImpl.findById(id);

            Map map = new HashMap<>();
            Map stepmap = StepMap(id);
            Map qamap = question(id);

            map.put("course_id", course.getId());
            map.put("course_name", course.getName());
            map.put("course_overview", course.getOverview());
            map.put("course_steps", stepmap);
            map.put("course_qa", qamap);

            Integer flag;

            Dataer dataer = dataerServiceImpl.findByDataerName(name);
            Set<Course> courses = dataer.getCourses();
            if (courses.contains(course))
                flag = 1;
            else
                flag = 0;

            map.put("flag", flag);

            return map;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 获取dataer创建的课程和参加的课程
    @ResponseBody
    @RequestMapping(value = "/mycourses", method = RequestMethod.POST)
    public Map myCourses(@RequestParam String username){
        try{
            Dataer dataer = dataerServiceImpl.findByDataerName(username);

            Map map = new HashMap<>();
            Map myPcourses = new HashMap<>();
            Map myHcourses = new HashMap<>();

            List<Map> join = new ArrayList<>();
//            myPcourses.put("join_courses", dataer.getCourses());
            for (Course course : dataer.getCourses()) {
                Map temp = new HashMap<>();
                temp.put("id", course.getId());
                temp.put("name", course.getName());
                temp.put("hoster", course.getHoster().getName());
                temp.put("description", course.getDescription());

                join.add(temp);
            }
            myPcourses.put("join_courses", join);

//            myHcourses.put("host_courses", dataer.getHostCourses());
            List<Map> host = new ArrayList<>();
            for (Course course : dataer.getHostCourses()) {
                Map temp = new HashMap<>();
                temp.put("id", course.getId());
                temp.put("name", course.getName());
                temp.put("hoster", course.getHoster().getName());
                temp.put("description", course.getDescription());

                host.add(temp);
            }
            myHcourses.put("host_courses", host);

            map.put("join", myPcourses);
            map.put("host", myHcourses);

            return map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 修改overview
    @ResponseBody
    @RequestMapping(value = "/editoverview", method = RequestMethod.POST)
    public boolean editOverview(@RequestParam Long id, @RequestParam String content){
        try{
            Course course = courseServiceImpl.findById(id);
            course.setOverview(content);

            courseServiceImpl.save(course);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // 添加step
//    @ResponseBody
//    @RequestMapping(value = "/addstep", method = RequestMethod.POST)
//    public boolean addStep(@RequestParam Long id, @RequestParam String name, @RequestParam String content){
//        try{
//            Step step = new Step();
//        }catch (Exception e){
//
//        }
//    }

    @Autowired
    DataFileRepository dataFileRepository;

    @Autowired
    QiniuService qiniuService;

    // step上传图片
//    @ResponseBody
//    @RequestMapping(value = "/confirmsteppic", method = RequestMethod.POST)
//    public boolean confirmStepPic(@RequestParam String key, @RequestParam Long stepId){
//        System.out.println("===============");
//        System.out.println("Step pic "+key+" is confirmed");
//        System.out.println("===============");
//
//        Long fileid = Long.parseLong(key.substring(0, key.lastIndexOf('.')));
//        DataFile dataFile = dataFileRepository.findById(fileid);
//
//        String url = qiniuService.createDownloadUrl(dataFile);
//
//        if (dataFile == null) {
//            return false;
//        }
//
//        Step step = stepServiceImpl.findByCourse()
//        if (competition == null) {
//            return false;
//        }
//
//    }


}

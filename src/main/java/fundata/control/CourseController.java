package fundata.control;

import fundata.model.*;
import fundata.repository.DataFileRepository;
import fundata.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    /*添加问题*/
    @ResponseBody
    @RequestMapping(value = "/addquestion", method = RequestMethod.POST)
    public boolean add_question(@RequestParam String username, @RequestParam Long courseId, @RequestParam String content){
        try{
            String time = getCurrentTime();
            Course course = courseServiceImpl.findById(courseId);
            Dataer dataer = dataerServiceImpl.findByDataerName(username);
            Question question = new Question();
            question.setUpdatetime(time);
            question.setCreatetime(time);
            question.setContent(content);
            question.setDataer(dataer);
            question.setCourse(course);
            questionServiceImpl.save(question);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    /*添加问题的回答*/
    @ResponseBody
    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public boolean add_answer(@RequestParam String username, @RequestParam Long questionId, @RequestParam String content){
        try{
            Question question = questionServiceImpl.findById(questionId);
            if (!question.getAnswered()) {
                question.setAnswered(true);
                questionServiceImpl.save(question);
            }
            Answer answer = new Answer();
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
            questionMap.put("head_href", q.getDataer().getHead_href());
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
                    answerMap.put("head_href", a.getQuestion().getDataer().getHead_href());
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
            st.put("id", t.getStepid());
            st.put("step_header",t.getStepname());
            st.put("step_content",t.getContent());
            st.put("step_pic_url",t.getPictureUrl());
            stepList.add(st);
        }
        course_steps.put("course_steps",stepList);
        return course_steps;
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

    //add step
    @ResponseBody
    @RequestMapping(value = "/addstep", method = RequestMethod.POST)
    public Map addStep(@RequestParam Long id, @RequestParam String name, @RequestParam String content){
        try{
            Course course = courseServiceImpl.findById(id);
            Step step = new Step();
            step.setStepname(name);
            step.setContent(content);
            step.setCourse(course);
            step.setPictureUrl("http://localhost:3000/images/loginbg.jpg");
            //step.setPictureUrl("dsf");
            stepServiceImpl.save(step);
            Map map = new HashMap();
            map.put("step_id", step.getStepid());
            map.put("step_header", step.getStepname());
            map.put("step_content", step.getContent());
            map.put("step_pic_url", step.getPictureUrl());
            return map;
        }catch (Exception e){
            e.printStackTrace();;
            return null;
        }
    }

    // 修改step的文字信息
    @ResponseBody
    @RequestMapping(value = "editstepcontent", method = RequestMethod.POST)
    public boolean editStepContent(@RequestParam Long id, @RequestParam String content){
        try{
            Step step = stepServiceImpl.findById(id);

            step.setContent(content);
            stepServiceImpl.save(step);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Autowired
    DataFileRepository dataFileRepository;

    @Autowired
    QiniuService qiniuService;

    // step上传图片
    @ResponseBody
    @RequestMapping(value = "/confirmsteppic", method = RequestMethod.POST)
    public Map confirmStepPic(@RequestParam String key, @RequestParam Long stepId, @RequestParam Long courseId){
        System.out.println("===============");
        System.out.println("Step pic "+key+" is confirmed");
        System.out.println("===============");

        Long fileid = Long.parseLong(key.substring(0, key.lastIndexOf('.')));
        DataFile dataFile = dataFileRepository.findById(fileid);

        String url = qiniuService.createDownloadUrl(dataFile);

        if (dataFile == null) {
            return null;
        }

        Step step = stepServiceImpl.findById(stepId);
        if (step == null) {
            return null;
        }
        else {
            step.setPictureUrl(url);
            stepServiceImpl.save(step);
            Map map =new HashMap<>();
            map.put("url", url);
            return map;
        }

    }


}

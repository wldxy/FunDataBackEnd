package fundata.control;

import fundata.model.*;
import fundata.repository.CourseRepository;
import fundata.service.*;
import fundata.viewmodel.BCourse;
import fundata.viewmodel.TopClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.crypto.Data;
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
    * 添加step
    * */
    @ResponseBody
    @RequestMapping(value = "/{courseId}/step{num}/{stepname}/{content}",method = RequestMethod.GET)
    public boolean addStep(@PathVariable Long courseId,@PathVariable String num,@PathVariable String stepname,@PathVariable String content){
       try {
           int number = Integer.parseInt(num);
           if(number > courseServiceImpl.getStepNum(courseId)){
               /*设置step内容*/
               Step step = new Step();
               step.setStepname(stepname);
               step.setContent(content);
               int key = Integer.parseInt(courseId.toString()+ num);
               step.setStepid(key);
               /*找到对应course*/
               Course course = courseServiceImpl.findById(courseId);
               step.setCourse(course);
               stepServiceImpl.save(step);
               courseServiceImpl.increaseStep(courseId);
           }else {
               stepServiceImpl.updateStepContent(content,number);
           }
           return true;
       }catch (Exception ex){
           return false;
       }
    }

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
        Map stepmap = StepMap(courseId);
        Map questionmap = question(courseId);
        return questionmap;
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



}

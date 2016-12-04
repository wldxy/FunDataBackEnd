package fundata.viewmodel;

import fundata.model.Course;

import java.util.List;

/**
 * Created by stanforxc on 2016/12/4.
 */
public class CourseDetail {
    private Long course_id;
    private String course_name;
    private String course_url;
    private List<CourseStep> courseSteps;

    public CourseDetail(Long course_id, String course_name, String course_url) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.course_url = course_url;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_url() {
        return course_url;
    }

    public void setCourse_url(String course_url) {
        this.course_url = course_url;
    }

    public List<CourseStep> getCourseSteps() {
        return courseSteps;
    }

    public void setCourseSteps(List<CourseStep> courseSteps) {
        this.courseSteps = courseSteps;
    }
}

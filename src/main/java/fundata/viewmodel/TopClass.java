package fundata.viewmodel;

/**
 * Created by stanforxc on 2016/12/4.
 */
public class TopClass {
    private Long course_id;
    private String course_name;
    private int course_rank;
    private int course_join_sum;
    private String course_teacher_name;

    public TopClass(Long course_id, String course_name, int course_rank, int course_join_sum, String course_teacher_name) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.course_rank = course_rank;
        this.course_join_sum = course_join_sum;
        this.course_teacher_name = course_teacher_name;
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

    public int getCourse_rank() {
        return course_rank;
    }

    public void setCourse_rank(int course_rank) {
        this.course_rank = course_rank;
    }

    public int getCourse_join_sum() {
        return course_join_sum;
    }

    public void setCourse_join_sum(int course_join_sum) {
        this.course_join_sum = course_join_sum;
    }

    public String getCourse_teacher_name() {
        return course_teacher_name;
    }

    public void setCourse_teacher_name(String course_teacher_name) {
        this.course_teacher_name = course_teacher_name;
    }
}

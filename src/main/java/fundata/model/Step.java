package fundata.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by stanforxc on 2016/12/5.
 */
@Entity
@Table(name = "step")
public class Step{
    @Id
    @Column(name = "stepid",nullable = false)
    private int stepid;

    @ManyToOne
    private Course course;

    @Column(name = "stepname",nullable = false)
    private String stepname;

    @Column(name = "content")
    private String content;

    @Column(name = "pictureUrl")
    private String pictureUrl;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getStepid() {
        return stepid;
    }

    public void setStepid(int stepid) {
        this.stepid = stepid;
    }


    public String getStepname() {
        return stepname;
    }

    public void setStepname(String stepname) {
        this.stepname = stepname;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}

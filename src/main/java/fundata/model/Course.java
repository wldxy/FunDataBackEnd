package fundata.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by stanforxc on 2016/12/2.
 */
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "name",nullable = false,length = 50)
    private String name;

    @Column(name = "teacher",nullable = false,length = 20)
    private String teacher;

    @Column(name = "description",length = 50)
    private String description;

    @Column(name = "overviewUrl",length = 50)
    private String overviewUrl;

    @Column(name = "questionUrl",length = 50)
    private String questionUrl;

    @Column(name = "stepUrl",length = 50)
    private String stepUrl;

    @Column(name = "commentUrl",length = 50)
    private String commentUrl;

    @Column(name = "pictureUrl",length = 50)
    private String pictureUrl;

    @Column(name = "registerNum")
    private int registerNum = 0;

    @ManyToMany
   private Set<Dataer> dataers = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOverviewUrl() {
        return overviewUrl;
    }

    public void setOverviewUrl(String overviewUrl) {
        this.overviewUrl = overviewUrl;
    }

    public String getQuestionUrl() {
        return questionUrl;
    }

    public void setQuestionUrl(String questionUrl) {
        this.questionUrl = questionUrl;
    }

    public String getStepUrl() {
        return stepUrl;
    }

    public void setStepUrl(String stepUrl) {
        this.stepUrl = stepUrl;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }

    public Set<Dataer> getDataers() {
        return dataers;
    }

    public void setDataers(Set<Dataer> dataers) {
        this.dataers = dataers;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(int registerNum) {
        this.registerNum = registerNum;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}

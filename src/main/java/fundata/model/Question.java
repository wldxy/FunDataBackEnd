package fundata.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by stanforxc on 2016/12/4.
 */
@Entity
@Table(name = "question")
public class Question {
    @Id
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "createtime")
    private String createtime;

    @Column(name = "updatetime")
    private String updatetime;

    @Column(name = "content")
    private String content;

    @Column(name = "answered")
    private boolean answered;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Dataer dataer;

    @OneToMany
    private Set<Answer> answers = new HashSet<>();




    public boolean getAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Dataer getDataer() {
        return dataer;
    }

    public void setDataer(Dataer dataer) {
        this.dataer = dataer;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }


    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
}

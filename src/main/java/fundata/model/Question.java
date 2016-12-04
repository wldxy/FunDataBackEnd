package fundata.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by stanforxc on 2016/12/4.
 */
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "createtime")
    private Date createtime;

    @Temporal(TemporalType.DATE)
    @Column(name = "updatetime")
    private Date updatetime;

    @Column(name = "content")
    private String content;

    @Column(name = "answered")
    private boolean answered;

    @ManyToOne
    private Course course;




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


    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}

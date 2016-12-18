package fundata.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
//
//    @Column(name = "teacher",nullable = false,length = 20)
//    private String teacher;

    @Column(name = "description",length = 50)
    private String description;

    @Column(name = "overview",length = 50)
    private String overview;

    @Column(name = "step")
    private int step;

    @Column(name = "pictureUrl",length = 50)
    private String pictureUrl;

    @Column(name = "registerNum")
    private int registerNum;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Dataer> dataers = new HashSet<>();

    @ManyToOne
    private Dataer hoster;

    public Set<Step> getSteps() {
        return steps;
    }

    public void setSteps(Set<Step> steps) {
        this.steps = steps;
    }

    @OneToMany(mappedBy = "course")
    private Set<Step> steps = new HashSet<>();

    @OneToMany
    private Set<Commentcs> courseComments = new HashSet<>();

    public Course() { }

    public Course(String name, Dataer hoster, String description, int step, String pictureUrl, int registerNum) {
        this.name = name;
//        this.teacher = teacher;
        this.hoster = hoster;
        this.description = description;
        this.step = step;
        this.pictureUrl = pictureUrl;
        this.registerNum = registerNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonBackReference
    public Dataer getHoster() {
        return hoster;
    }

    public void setHoster(Dataer hoster) {
        this.hoster = hoster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public String getTeacher() {
//        return teacher;
//    }
//
//    public void setTeacher(String teacher) {
//        this.teacher = teacher;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
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

    @JsonBackReference
    public Set<Dataer> getDataers() {
        return dataers;
    }

    public void setDataers(Set<Dataer> dataers) {
        this.dataers = dataers;
    }

    public Set<Commentcs> getCourseComments() {
        return courseComments;
    }

    public void setCourseComments(Set<Commentcs> courseComments) {
        this.courseComments = courseComments;
    }

}

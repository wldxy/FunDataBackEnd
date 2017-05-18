package fundata.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ocean on 16-11-24.
 */
@Entity(name = "dataer")
@Table(name = "dataer")
public class Dataer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dataer_id", nullable = false)
    private Long id;

    @Column(name = "password", length = 2048, nullable = false)
    private String password;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "role")
    @JsonBackReference
    private Integer user_flag;

    @Column(name = "head_href")
    private String head_href;

    @OneToMany(mappedBy = "hoster")
    private Set<Competition> hostcompetition;

    @ManyToMany
    private Set<Competition> competitions;

    @OneToMany(mappedBy = "dataset")
    private List<DataerDataset> datasets = new ArrayList<>();

    @OneToMany(mappedBy = "dataer")
    private Set<PullRequest> pullRequests = new HashSet<>();

    @OneToMany(mappedBy = "dataer")
    private Set<Commentcomp> commentcompSet;

    @OneToMany(mappedBy = "dataer")
    private Set<Accurate> accurates = new HashSet<>();

    @ManyToMany(mappedBy = "dataers", cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "hoster", cascade = CascadeType.ALL)
    private Set<Course> hostCourses = new HashSet<>();

    public Dataer() { }

    public Dataer(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Course> getHostCourses() {
        return hostCourses;
    }

    public void setHostCourses(Set<Course> hostCourses) {
        this.hostCourses = hostCourses;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataerDataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<DataerDataset> datasets) {
        this.datasets = datasets;
    }

    public int getUser_flag() {
        return user_flag;
    }

    public void setUser_flag(int user_flag) {
        this.user_flag = user_flag;
    }

    public Set<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(Set<Competition> competitions) {
        this.competitions = competitions;
    }

    public String getHead_href() {
        return head_href;
    }

    public void setHead_href(String head_href) {
        this.head_href = head_href;
    }

    public Set<Competition> getHostCompetition() {
        return hostcompetition;
    }

    public void setHostCompetition(Set<Competition> hostCompetition) {
        this.hostcompetition = hostCompetition;
    }

    public Set<Commentcomp> getCommentcompSet() {
        return commentcompSet;
    }

    public void setCommentcompSet(Set<Commentcomp> commentcompSet) {
        this.commentcompSet = commentcompSet;
    }

    public Set<Accurate> getAccurates() {
        return accurates;
    }

    public void setAccurates(Set<Accurate> accurates) {
        this.accurates = accurates;
    }

    public Set<Competition> getHostcompetition() {
        return hostcompetition;
    }

    public void setHostcompetition(Set<Competition> hostcompetition) {
        this.hostcompetition = hostcompetition;
    }

    public Set<PullRequest> getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(Set<PullRequest> pullRequests) {
        this.pullRequests = pullRequests;
    }

}

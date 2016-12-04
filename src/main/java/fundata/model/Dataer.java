package fundata.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ocean on 16-11-24.
 */
@Entity(name = "dataer")
@Table(name = "dataer")
public class Dataer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToMany
    private Set<Dataset> datasets = new HashSet<>();

    @ManyToMany
   private Set<Course> courses = new HashSet<>();  //dataer_course表

    public Dataer() { }

    public Dataer(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
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

    public Set<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(Set<Dataset> datasets) {
        this.datasets = datasets;
    }
/*
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
    */
}

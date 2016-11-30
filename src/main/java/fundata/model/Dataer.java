package fundata.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by ocean on 16-11-24.
 */
@Entity(name = "dataer")
@Table(name = "dataer")
public class Dataer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "name", length = 100)
    private String name;

    @ManyToMany
    private Set<Dataset> datasets;

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
}

package fundata.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by stanforxc on 2016/11/29.
 */

@Entity
@Table(name= "Dataset")
public class Dataset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "ID")
    private Long id;

    @Column(nullable = false,name = "NAME", length = 50)
    private String name;

    @ManyToMany(mappedBy = "datasets")
    private Set<Dataer> dataers = new HashSet<>();

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "dataset")
    private Set<DatasetTitle> datasetTitles = new HashSet<>();

    @OneToMany(mappedBy = "dataset")
    private Set<DataFile> files = new HashSet<>();

    @OneToMany(mappedBy = "dataset")
    private Set<PullRequest> pullRequests = new HashSet<>();

    public Dataset() { }

    public Dataset(String name) {
        this.name = name;
    }

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

    public Set<Dataer> getDataers() {
        return dataers;
    }

    public void setDataers(Set<Dataer> dataers) {
        dataers = dataers;
    }

    public Set<DataFile> getFiles() {
        return files;
    }

    public void setFiles(Set<DataFile> files) {
        this.files = files;
    }

    public Set<PullRequest> getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(Set<PullRequest> pullRequests) {
        this.pullRequests = pullRequests;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<DatasetTitle> getDatasetTitles() {
        return datasetTitles;
    }

    public void setDatasetTitles(Set<DatasetTitle> datasetTitles) {
        this.datasetTitles = datasetTitles;
    }
}

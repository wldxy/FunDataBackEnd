package fundata.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
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
    @Column(nullable = false,name = "dataset_id")
    private Long id;

    @Column(nullable = false,name = "name", length = 50)
    private String name;

    @OneToMany(mappedBy = "dataer")
    @JsonBackReference
    private Set<DataerDataset> dataers = new HashSet<DataerDataset>();

    @Column(name = "ds_description")
    private String dsDescription;

    @Column(name = "cover_url")
    private String coverUrl;

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @Column(name = "create_time")
    @Temporal(TemporalType.DATE)
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFormatDescription() {
        return formatDescription;
    }

    public void setFormatDescription(String formatDescription) {
        this.formatDescription = formatDescription;
    }

    @Column(name = "format_description")
    private String formatDescription;


    @OneToMany(mappedBy = "dataset")
    private Set<DatasetTitle> datasetTitles = new HashSet<>();

    @OneToMany
    private Set<DataFile> files = new HashSet<>();

    @OneToOne
    private DataFile titleFile;

    @OneToMany(mappedBy = "dataset")
    private Set<PullRequest> pullRequests = new HashSet<>();

    @OneToMany(mappedBy = "dataset")
    private Set<DSComment> dsComments = new HashSet<>();

    @OneToOne
    DataFile allFile;

    public Dataset() { }

    public Dataset(String name) {
        this.name = name;
    }

    public DataFile getTitleFile() {
        return titleFile;
    }

    public void setTitleFile(DataFile titleFile) {
        this.titleFile = titleFile;
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

    public DataFile getAllFile() {
        return allFile;
    }

    public void setAllFile(DataFile allFile) {
        this.allFile = allFile;
    }

    public Set<DataerDataset> getDataers() {
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

    public String getDsDescription() {
        return dsDescription;
    }

    public void setDsDescription(String dsDescription) {
        this.dsDescription = dsDescription;
    }

    public Set<DatasetTitle> getDatasetTitles() {
        return datasetTitles;
    }

    public void setDatasetTitles(Set<DatasetTitle> datasetTitles) {
        this.datasetTitles = datasetTitles;
    }

    public Set<DSComment> getDsComments() {
        return dsComments;
    }

    public void setDsComments(Set<DSComment> dsComments) {
        this.dsComments = dsComments;
    }
}

package fundata.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.*;

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

    @Column(name = "create_time", columnDefinition="DATETIME")
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
    private List<PullRequest> pullRequests = new ArrayList<>();

    @OneToMany(mappedBy = "dataset")
    private Set<DSComment> dsComments = new HashSet<>();

    @OneToOne
    private DataFile file;

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

    public DataFile getFile() {
        return file;
    }

    public void setFile(DataFile file) {
        this.file = file;
    }

    public Set<DataerDataset> getDataers() {
        return dataers;
    }

    public void setDataers(Set<Dataer> dataers) {
        dataers = dataers;
    }

    public List<PullRequest> getPullRequests() {
        return pullRequests;
    }

    public void setPullRequests(List<PullRequest> pullRequests) {
        this.pullRequests = pullRequests;
    }

    public String getDsDescription() {
        return dsDescription;
    }

    public void setDsDescription(String dsDescription) {
        this.dsDescription = dsDescription;
    }

    public Set<DSComment> getDsComments() {
        return dsComments;
    }

    public void setDsComments(Set<DSComment> dsComments) {
        this.dsComments = dsComments;
    }
}

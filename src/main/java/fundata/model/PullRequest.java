package fundata.model;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ocean on 16-12-6.
 */
@Entity
@Table(name = "PullRequest")
public class PullRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    // 1 means success 0 means waiting -1 means file waiting to check
    @Column(name = "status")
    private Integer status;

    @Column(name = "updatetime")
    @Temporal(TemporalType.DATE)
    private Date updatetime;

    @OneToOne
    private DataFile dataFile;

    @ManyToOne
    private Dataer dataer;

    @ManyToOne
    private Dataset dataset;

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Dataer getDataer() {
        return dataer;
    }

    public void setDataer(Dataer dataer) {
        this.dataer = dataer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(DataFile dataFile) {
        this.dataFile = dataFile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
}

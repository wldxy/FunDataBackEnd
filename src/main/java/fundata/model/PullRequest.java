package fundata.model;

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
    private short status;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Column(name = "tag")
    private String tag;

    @Column(name = "update_time")
    @Temporal(TemporalType.DATE)
    private Date updateTime;

    @OneToOne
    private DataFile dataFile;

    @ManyToOne
    private Dataer dataer;

    @ManyToOne
    private Dataset dataset;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }
}

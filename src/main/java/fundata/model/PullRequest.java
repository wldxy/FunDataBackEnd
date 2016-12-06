package fundata.model;

import com.sun.org.glassfish.gmbal.Description;

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

    @Column(name = "status")
    private Integer status;

    @Column(name = "updatetime")
    private Date updatetime;

    private DataFile dataFile;

    private Dataer dataer;

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

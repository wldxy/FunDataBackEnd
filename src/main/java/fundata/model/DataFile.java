package fundata.model;

import oracle.sql.TIMESTAMP;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by stanforxc on 2016/11/29.
 */
@Entity
@Table(name = "DataFile")
public class DataFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "createTime", updatable = false)
    @Temporal(TemporalType.DATE)
    private Date createTime;

    @Column(name = "updateTime")
    @Temporal(TemporalType.DATE)
    private Date updateTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "suffix")
    private String suffix;

    @ManyToOne
    private Dataset dataset;

    public String getFileName() {
        return this.id.toString() + "." + suffix;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getFileid() {
        return id;
    }

    public void setFileid(Long fileid) {
        this.id = fileid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Dataset getDatasets() {
        return dataset;
    }

    public void setDatasets(Dataset datasets) {
        this.dataset = datasets;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}

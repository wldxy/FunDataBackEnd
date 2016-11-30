package FunData.Model;

import oracle.sql.TIMESTAMP;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by stanforxc on 2016/11/29.
 */
@Entity
@Table(name = "DataFile")
public class DataFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileid")
    private Long fileid;

    @Column(name="createTime")
    private TIMESTAMP createTime;

    @Column(name = "updateTime")
    private TIMESTAMP updateTime;

    @Column(name = "filename",length = 50)
    private String filename;

    @ManyToMany
    private Set<Dataset> datasets;

    public Long getFileid() {
        return fileid;
    }

    public void setFileid(Long fileid) {
        this.fileid = fileid;
    }

    public TIMESTAMP getCreateTime() {
        return createTime;
    }

    public void setCreateTime(TIMESTAMP createTime) {
        this.createTime = createTime;
    }

    public TIMESTAMP getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(TIMESTAMP updateTime) {
        this.updateTime = updateTime;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Set<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(Set<Dataset> datasets) {
        this.datasets = datasets;
    }
}

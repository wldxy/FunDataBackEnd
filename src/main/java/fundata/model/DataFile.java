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

    @Column(name = "name")
    private String name;

    @Column(name = "oldname")
    private String oldname;

    @Column(name = "createTime", updatable = false)
    @Temporal(TemporalType.DATE)
    private Date createTime;

    @Column(name = "updateTime")
    @Temporal(TemporalType.DATE)
    private Date updateTime;

    @Column(name = "status")
    private Integer status;

    public String getOldname() {
        return oldname;
    }

    public void setOldname(String oldname) {
        this.oldname = oldname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}

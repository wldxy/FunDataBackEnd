package fundata.model;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "url")
    private String url;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

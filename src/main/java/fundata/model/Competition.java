package fundata.model;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Set;

/**
 * Created by stanforxc on 2016/11/29.
 */

@Entity
@Table(name = "Competition")
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String  name;

    @Column(name = "active")
    private boolean active;

    @Column(name = "des")
    private String des;

    @Column(name = "registerNum")
    private int registerNum;

    @Column(name = "starttime")
    private String starttime;

    @Column(name = "endtime")
    private String endtime;

    @ManyToOne
    private Dataer hoster;

    @ManyToMany(mappedBy = "competitions")
    private Set<Dataer> dataers;  //参与者

    @OneToMany(mappedBy = "competition")
    private Set<CommentComp> commentComps;

    @OneToMany
    private Set<DataFile> dataFile;


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


    public Set<DataFile> getDataFile() {
        return dataFile;
    }

    public void setDataFile(Set<DataFile> dataFile) {
        this.dataFile = dataFile;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(int registerNum) {
        this.registerNum = registerNum;
    }

    public Set<Dataer> getDataers() {
        return dataers;
    }

    public void setDataers(Set<Dataer> dataers) {
        this.dataers = dataers;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Set<CommentComp> getCommentComps() {
        return commentComps;
    }

    public void setCommentComps(Set<CommentComp> commentComps) {
        this.commentComps = commentComps;
    }

    public Dataer getHoster() {
        return hoster;
    }

    public void setHoster(Dataer hoster) {
        this.hoster = hoster;
    }
}

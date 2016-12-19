package fundata.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletionService;

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

    @ManyToMany(mappedBy = "competitions", cascade = CascadeType.ALL)
    private Set<Dataer> dataers = new HashSet<>();  //参与者

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
    private Set<Commentcomp> commentComps = new HashSet<>();

    @OneToMany
    private Set<DataFile> dataFile = new HashSet<>();

    @OneToOne
    private DataFile ansFile;

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

    @JsonBackReference
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

    @JsonBackReference
    public Set<Commentcomp> getCommentComps() {
        return commentComps;
    }

    public void setCommentComps(Set<Commentcomp> commentComps) {
        this.commentComps = commentComps;
    }

    @JsonBackReference
    public Dataer getHoster() {
        return hoster;
    }

    public void setHoster(Dataer hoster) {
        this.hoster = hoster;
    }

    public DataFile getAnsFile() {
        return ansFile;
    }

    public void setAnsFile(DataFile ansFile) {
        this.ansFile = ansFile;
    }
}

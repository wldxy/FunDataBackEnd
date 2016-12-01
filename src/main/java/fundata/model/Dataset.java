package fundata.model;


import fundata.model.DataFile;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by stanforxc on 2016/11/29.
 */

@Entity
@Table(name= "Dataset")
public class Dataset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "ID")
    private Long id;

    @Column(nullable = false,name="NAME",length = 50)
    private String name;

    @ManyToMany
    private Set<Dataer> dataers = new HashSet<>();

    @ManyToMany
    private Set<DataFile> files;

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


    public Set<Dataer> getDataers() {
        return dataers;
    }

    public void setDataers(Set<Dataer> dataers) {
        dataers = dataers;
    }


    public Set<DataFile> getFiles() {
        return files;
    }

    public void setFiles(Set<DataFile> files) {
        this.files = files;
    }
}

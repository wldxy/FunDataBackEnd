package FunData.Model;


import javax.persistence.*;
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
    private Set<Dataer> Dataers;

    @ManyToMany
    private Set<DataFile> files;


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
        return Dataers;
    }

    public void setDataers(Set<Dataer> dataers) {
        Dataers = dataers;
    }


    public Set<DataFile> getFiles() {
        return files;
    }

    public void setFiles(Set<DataFile> files) {
        this.files = files;
    }
}

package fundata.model;

import javax.persistence.*;

/**
 * Created by stanforxc on 2016/12/13.
 */
@Entity
@Table(name = "accurate")
public class Accurate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private Double value;

    @Column(name = "uploadDate")
    private String uploadDate;

    @ManyToOne
    private Dataer dataer;

    @OneToOne
    private DataFile dataFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dataer getDataer() {
        return dataer;
    }

    public void setDataer(Dataer dataer) {
        this.dataer = dataer;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(DataFile dataFile) {
        this.dataFile = dataFile;
    }
}

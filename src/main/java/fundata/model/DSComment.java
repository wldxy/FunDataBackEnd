package fundata.model;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by ocean on 16-12-12.
 */
@Entity
@Table(name = "DSComment")
public class DSComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", length = 255)
    private String content;

    @Column(name = "time")
    @Temporal(TemporalType.DATE)
    private Date time;

    @ManyToOne
    private Dataset dataset;

    @ManyToOne
    private Dataer dataer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public Dataer getDataer() {
        return dataer;
    }

    public void setDataer(Dataer dataer) {
        this.dataer = dataer;
    }
}

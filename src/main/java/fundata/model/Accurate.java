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

    @ManyToOne
    private Dataer dataer;


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
}

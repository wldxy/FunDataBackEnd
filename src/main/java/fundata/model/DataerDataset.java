package fundata.model;

import javax.persistence.*;

/**
 * Created by huang on 17-4-21.
 */
@Entity
@Table(name= "DataerDataset")
@IdClass(DataerDatasetId.class)
public class DataerDataset {

    @Id
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="dataer_id",nullable=false, insertable=false, updatable=false)
    private Dataer dataer;

    @Id
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="dataset_id",nullable=false, insertable=false, updatable=false)
    private Dataset dataset;

    @Column(name = "role")
    private short role;

    public Dataer getDataer() {
        return dataer;
    }

    public DataerDataset() {

    }

    public DataerDataset(Dataer dataer, Dataset dataset, short role) {

        this.dataer = dataer;
        this.dataset = dataset;
        this.role = role;
    }

    public void setDataer(Dataer dataer) {
        this.dataer = dataer;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }
}

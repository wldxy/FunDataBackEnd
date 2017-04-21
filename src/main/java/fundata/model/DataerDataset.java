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
    private Dataer dataerId;

    @Id
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="dataset_id",nullable=false, insertable=false, updatable=false)
    private Dataset datasetId;

    @Column(name = "role")
    private short role;

    public Dataer getDataerId() {
        return dataerId;
    }

    public DataerDataset() {

    }

    public DataerDataset(Dataer dataerId, Dataset datasetId, short role) {

        this.dataerId = dataerId;
        this.datasetId = datasetId;
        this.role = role;
    }

    public void setDataerId(Dataer dataerId) {
        this.dataerId = dataerId;
    }

    public Dataset getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Dataset datasetId) {
        this.datasetId = datasetId;
    }

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }
}

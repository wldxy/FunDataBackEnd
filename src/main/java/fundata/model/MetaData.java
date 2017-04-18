package fundata.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by huang on 17-4-18.
 */
@Entity
@Table(name= "MetaData")
public class MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Dataset dataset;

    @Column(name = "col_name")
    private String colName;

    @Column(name = "col_type")
    private int colType;


    public MetaData(String colName, int colType, Dataset dataset) {
        this.colName = colName;
        this.colType = colType;
        this.dataset = dataset;
    }
}

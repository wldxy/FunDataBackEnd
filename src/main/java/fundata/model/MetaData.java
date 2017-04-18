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
    private String name;

    @Column(name = "col_type")
    private String type;


    public MetaData(String colName, String colType) {
        this.name = colName;
        this.type = colType;
    }
}

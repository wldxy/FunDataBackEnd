package fundata.document;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.List;

/**
 * Created by huang on 17-4-19.
 */
@Document
public class DatasetMeta {

    @Id
    private Long datasetId;

    @Field("tables")
    private List<Table> tables;
    
    @Field("expressions")
    private List<String> expressions;

    public List<String> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<String> expressions) {
        this.expressions = expressions;
    }

    public DatasetMeta(Long datasetId, List<Table> tables) {
        this.datasetId = datasetId;
        this.tables = tables;
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}

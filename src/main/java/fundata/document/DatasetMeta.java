package fundata.document;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.List;
import java.util.Map;

/**
 * Created by huang on 17-4-19.
 */
@Document
public class DatasetMeta {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Field("dataset_id")
    private Long datasetId;

    @Field("tables")
    private List<Table> tables;
    
    @Field("expressions")
    private Map<String, List<String>> expressions;

    public List<String> getForeigns() {
        return foreigns;
    }

    public void setForeigns(List<String> foreigns) {
        this.foreigns = foreigns;
    }

    @Field("foreigns")
    private List<String> foreigns;

    public Map<String, List<String>> getExpressions() {
        return expressions;
    }

    public void setExpressions(Map<String, List<String>> expressions) {
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

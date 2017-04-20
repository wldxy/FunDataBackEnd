package fundata.document;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

/**
 * Created by huang on 17-4-19.
 */
@Document
public class MetaData {
    public MetaData(Long datasetId, List<Field> fields) {
        this.fields = fields;
        this.datasetId = datasetId;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    private List<Field> fields;
    @Id
    private Long datasetId;
}

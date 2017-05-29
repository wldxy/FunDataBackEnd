package fundata.viewmodel;

import fundata.document.Field;
import fundata.document.Table;

import java.util.List;
import java.util.Map;

/**
 * Created by huang on 17-5-13.
 */
public class PullRequestDetail {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Table[] columns;
    private List<Map<String, Double>> limits;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Table[] getColumns() {
        return columns;
    }

    public void setColumns(Table[] columns) {
        this.columns = columns;
    }

    public List<Map<String, Double>> getLimits() {
        return limits;
    }

    public void setLimits(List<Map<String, Double>> limits) {
        this.limits = limits;
    }
}

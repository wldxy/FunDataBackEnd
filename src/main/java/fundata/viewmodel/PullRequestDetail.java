package fundata.viewmodel;

import fundata.document.Table;

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
    private Map<String, String> limits;
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

    public Map<String, String> getLimits() {
        return limits;
    }

    public void setLimits(Map<String, String> limits) {
        this.limits = limits;
    }
}

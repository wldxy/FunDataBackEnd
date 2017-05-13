package fundata.viewmodel;

import fundata.document.Field;

import java.util.List;
import java.util.Map;

/**
 * Created by huang on 17-5-13.
 */
public class PullRequestDetail {
    private Field[] columns;
    private List<Map<String, Double>> limits;

    public Field[] getColumns() {
        return columns;
    }

    public void setColumns(Field[] columns) {
        this.columns = columns;
    }

    public List<Map<String, Double>> getLimits() {
        return limits;
    }

    public void setLimits(List<Map<String, Double>> limits) {
        this.limits = limits;
    }
}

package fundata.document;

import java.util.List;

/**
 * Created by huang on 17-5-17.
 */
public class Table {

    private String name;
    private List<Field> columns;

    public List<Field> getColumns() {
        return columns;
    }

    public void setColumns(List<Field> columns) {
        this.columns = columns;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

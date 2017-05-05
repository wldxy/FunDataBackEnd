package fundata.document;


import com.sun.xml.internal.ws.developer.Serialization;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by huang on 17-4-18.
 */
public class Field {

    private String colName;
    private String colType;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColType() {
        return colType;
    }

    public void setColType(String colType) {
        this.colType = colType;
    }

    public List<Short> getLimits() {
        return limits;
    }

    public void setLimits(List<Short> limits) {
        this.limits = limits;
    }

    private List<Short> limits = new LinkedList<>();

    public Field(String colName, String colType, List<Short> limits) {
        this.colName = colName;
        this.colType = colType;
        this.limits = limits;
    }
}

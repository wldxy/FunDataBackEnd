package fundata.document;


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
    private List<Short> limits = new LinkedList<>();

    public Field(String colName, String colType, List<Short> limits) {
        this.colName = colName;
        this.colType = colType;
        this.limits = limits;
    }
}

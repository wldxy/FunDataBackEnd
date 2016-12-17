package fundata.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ocean on 16-12-13.
 */
public class DatasetContent {
    private Integer contribute;

    private String description;

    private Integer admin;

    private Integer count;

    private List<Content> content = new ArrayList<>();

    public DatasetContent() { }

    public void addContent(String key ,String type, String meaning) {
        content.add(new Content(key, type, meaning));
    }

    public Integer getContribute() {
        return contribute;
    }

    public void setContribute(Integer contribute) {
        this.contribute = contribute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }
}

class Content {
    private String key;

    private String type;

    private String meaning;

    Content(String key ,String type, String meaning) {
        this.key = key;
        this.type = type;
        this.meaning = meaning;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
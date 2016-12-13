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

    public void addContent(String key ,String desc, String type) {
        content.add(new Content(key, desc, type));
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

    private String value;

    private String desc;

    Content(String key ,String desc, String type) {
        this.key = key;
        this.desc = desc;
        this.value = type;
    }
}
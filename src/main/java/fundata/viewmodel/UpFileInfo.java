package fundata.viewmodel;

/**
 * Created by ocean on 16-11-29.
 */
public class UpFileInfo {
    private String uptoken;
    private String key;

    public UpFileInfo() {

    }

    public UpFileInfo(String uptoken, String key) {
        this.uptoken = uptoken;
        this.key = key;
    }

    public String getUptoken() {
        return uptoken;
    }

    public void setUptoken(String uptoken) {
        this.uptoken = uptoken;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
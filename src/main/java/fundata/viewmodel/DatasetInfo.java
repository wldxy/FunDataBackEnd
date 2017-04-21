package fundata.viewmodel;

import java.util.Date;

/**
 * Created by huang on 17-4-21.
 */
public class DatasetInfo {
    private String name;
    private String ownerUrl;
    private String ownerName;

    public String getName() {
        return name;
    }

    public String getOwnerUrl() {
        return ownerUrl;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getDsDescription() {
        return dsDescription;
    }

    public String getFormatDescription() {
        return formatDescription;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerUrl(String ownerUrl) {
        this.ownerUrl = ownerUrl;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setDsDescription(String dsDescription) {
        this.dsDescription = dsDescription;
    }

    public void setFormatDescription(String formatDescription) {
        this.formatDescription = formatDescription;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private String dsDescription;
    private String formatDescription;
    private Date createTime;
}

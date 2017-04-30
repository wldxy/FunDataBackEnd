package fundata.viewmodel;

import java.util.Date;

/**
 * Created by huang on 17-4-21.
 */
public class DatasetInfo {
    private Long id;
    private String name;
    private String coverUrl;
    private String ownerName;
    private String dsDescription;
    private String formatDescription;
    private Date createTime;
    private int contributeNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoverUrl() {
        return coverUrl;
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

    public int getContributeNum() {
        return contributeNum;
    }

    public void setContributeNum(int contributeNum) {
        this.contributeNum = contributeNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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

}

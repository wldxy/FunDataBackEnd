package fundata.repository;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by ocean on 16-12-17.
 */
@ConfigurationProperties(prefix = "fundata.file")
@Component
public class FileProperties {

    private String ansFilePath;
    private String userAnsPath;
    private String titlePath;

    public String getAnsFilePath() {
        return ansFilePath;
    }

    public void setAnsFilePath(String ansFilePath) {
        this.ansFilePath = ansFilePath;
    }

    public String getUserAnsPath() {
        return userAnsPath;
    }

    public void setUserAnsPath(String userAnsPath) {
        this.userAnsPath = userAnsPath;
    }

    public String getTitlePath() {
        return titlePath;
    }

    public void setTitlePath(String titlePath) {
        this.titlePath = titlePath;
    }
}

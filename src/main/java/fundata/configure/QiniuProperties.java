package fundata.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by ocean on 16-11-29.
 */
@ConfigurationProperties(prefix = "fundata.qiniu")
@Component
public class QiniuProperties {
    private String access_key;
    private String secret_key;
    private String bucket_private;
    private String domain_private;

    public String getBucket_public() {
        return bucket_public;
    }

    public void setBucket_public(String bucket_public) {
        this.bucket_public = bucket_public;
    }

    public String getDomain_public() {
        return domain_public;
    }

    public void setDomain_public(String domain_public) {
        this.domain_public = domain_public;
    }

    private String bucket_public;
    private String domain_public;
    public String getDomain_private() {
        return domain_private;
    }

    public void setDomain_private(String domain_private) {
        this.domain_private = domain_private;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public String getBucket_private() {
        return bucket_private;
    }

    public void setBucket_private(String bucket_private) {
        this.bucket_private = bucket_private;
    }
}

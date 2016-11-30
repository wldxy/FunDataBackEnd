package fundata.service;

import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by ocean on 16-11-29.
 */
@Service
public class QiniuServiceImpl implements QiniuService {
    @Autowired
    private QiniuProperties qiniuProperties;

    private Auth auth;

    @PostConstruct
    public void init() {
        this.auth = Auth.create(qiniuProperties.getAccess_key(), qiniuProperties.getSecret_key());
    }

    @Override
    public String createUploadToken() {
        return auth.uploadToken(qiniuProperties.getBucket());
    }

    @Override
    public String createDownloadUrl() {
        return auth.privateDownloadUrl(qiniuProperties.getDomain());
    }
}

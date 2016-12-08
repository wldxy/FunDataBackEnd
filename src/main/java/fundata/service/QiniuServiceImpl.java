package fundata.service;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import fundata.model.DataFile;
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
    private Zone zone;
    private Configuration configuration;
    private BucketManager bucketManager;

    @PostConstruct
    public void init() {
        this.auth = Auth.create(qiniuProperties.getAccess_key(), qiniuProperties.getSecret_key());
        this.zone = Zone.autoZone();
        this.configuration = new Configuration(zone);
        this.bucketManager = new BucketManager(auth, configuration);
    }

    @Override
    public String createUploadToken(String key) {
        return auth.uploadToken(qiniuProperties.getBucket(), key, 3600,
                new StringMap().put("insertOnly", 1));
    }

    @Override
    public String createUploadToken(DataFile dataFile) {
        String key = dataFile.getFileid().toString() + ".csv";
        return this.createUploadToken(key);
    }

    @Override
    public String createDownloadUrl(DataFile dataFile) {
        String url = qiniuProperties.getDomain() + dataFile.getFileid() + ".csv";
        return auth.privateDownloadUrl(url, 3600);
    }

    @Override
    public void deleteFile(String fileName) throws QiniuException {
        bucketManager.delete(qiniuProperties.getBucket(), fileName);
    }
}

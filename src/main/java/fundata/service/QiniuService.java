package fundata.service;


import com.qiniu.common.QiniuException;

/**
 * Created by ocean on 16-11-29.
 */
public interface QiniuService {
    String createUploadToken(String key);

    String createDownloadUrl(String key);

    void deleteFile(String fileName) throws QiniuException;
}

package fundata.service;


/**
 * Created by ocean on 16-11-29.
 */
public interface QiniuService {
    String createUploadToken();

    String createDownloadUrl();
}

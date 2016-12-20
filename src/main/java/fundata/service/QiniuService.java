package fundata.service;


import com.qiniu.common.QiniuException;
import fundata.model.DataFile;
import fundata.model.Dataer;
import fundata.model.Dataset;

/**
 * Created by ocean on 16-11-29.
 */
public interface QiniuService {
    String createUploadToken(String key);

//    String createUploadToken(DataFile dataFile);

    String createDownloadUrl(DataFile dataFile);

    String createUploadToken();

    void deleteFile(String fileName) throws QiniuException;

    String downloadFile(DataFile dataFile, String dir);

    String createDownloadUrl(Dataset dataset);

    void downloadFile(String url, String fileName, String dir);

    int getFileSize(DataFile dataFile);

    void uploadFile(String path, String key);
}

package fundata.service;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import fundata.model.DataFile;
import fundata.model.Dataset;
import fundata.repository.QiniuProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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
//        return auth.uploadToken(qiniuProperties.getBucket(), key, 3600,
//                new StringMap().put("insertOnly", 1));
        System.out.println("===================");
        System.out.println(qiniuProperties.getBucket());
        System.out.println("===================");

        return auth.uploadToken(qiniuProperties.getBucket());
    }
//
//    @Override
//    public String createUploadToken(DataFile dataFile) {
//        String key = dataFile.getName();
//        String url = "http://" + qiniuProperties.getDomain() + "/" + key;
//        return this.createUploadToken(url);
//    }

    @Override
    public String createDownloadUrl(DataFile dataFile) {
        String url = "http://" + qiniuProperties.getDomain() + "/" + dataFile.getName();
        return url;
    }

    @Override
    public String createDownloadUrl(Dataset dataset) {
        String url = "http://" + qiniuProperties.getDomain() + "/" + dataset.getName() + ".csv";
        return url;
    }

    @Override
    public String createUploadToken() {
        return auth.uploadToken(qiniuProperties.getBucket());
    }

    @Override
    public void deleteFile(String fileName) throws QiniuException {
        bucketManager.delete(qiniuProperties.getBucket(), fileName);
    }

    @Override
    public void downloadFile(String urlPath, String fileName, String dir) {
        try {
            URL url = new URL(urlPath);

            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.connect();
//            int fileSize = httpURLConnection.getContentLength();

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            File file = new File(dir + "/"+ fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream outputStream = new FileOutputStream(file);
            int size = 0, len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                outputStream.write(buf, 0, size);
            }
            bin.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String downloadFile(DataFile dataFile, String dir) {
        String url = createDownloadUrl(dataFile);
        String fileName = dataFile.getName();
        this.downloadFile(url, fileName, dir);
        return url;
//        try {
//            URL url = new URL(createDownloadUrl(dataFile));
//            String fileName = dataFile.getFileName();
//
//            URLConnection urlConnection = url.openConnection();
//            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setRequestProperty("Charset", "UTF-8");
//            httpURLConnection.connect();
//            int fileSize = httpURLConnection.getContentLength();
//
//            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
//            File file = new File(dir + fileName);
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
//            OutputStream outputStream = new FileOutputStream(file);
//            int size = 0, len = 0;
//            byte[] buf = new byte[1024];
//            while ((size = bin.read(buf)) != -1) {
//                len += size;
//                outputStream.write(buf, 0, size);
//            }
//            bin.close();
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getFileSize(DataFile dataFile) {
        int fileSize = 0;
        try {
            URL url = new URL(createDownloadUrl(dataFile));

            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.connect();
            fileSize = httpURLConnection.getContentLength();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return fileSize;
        }
    }

    @Override
    public void uploadFile(String path, String key) {
        UploadManager uploadManager = new UploadManager(configuration);

        try {
            //调用put方法上传
            Response res = uploadManager.put(path, key, this.createUploadToken());
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
    }
}

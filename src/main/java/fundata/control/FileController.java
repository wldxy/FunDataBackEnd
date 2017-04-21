package fundata.control;

import fundata.model.DataFile;
import fundata.model.Dataset;
import fundata.repository.DataFileRepository;
import fundata.repository.DatasetRepository;
import fundata.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ocean on 16-11-29.
 */
@RestController
public class FileController {
    @Autowired
    @Qualifier("qiniuServiceImpl")
    private QiniuService qiniuService;

    @Autowired
    private DataFileRepository dataFileRepository;

    @Autowired
    private DatasetRepository datasetRepository;

//    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//    public UpFileInfo getUploadCSV() {
//        DataFile dataFile = new DataFile();
//        dataFile.setCreateTime(new Date());
//        dataFile.setUpdateTime(new Date());
//        dataFile.setStatus(0);
//        dataFile.setSuffix("csv");
//        dataFileRepository.save(dataFile);
//        String fileName = dataFile.getFileName();
//        return new UpFileInfo(qiniuService.createUploadToken(fileName), fileName);
//    }
//
//    @RequestMapping(value = "/confirmUpload", method = RequestMethod.POST)
//    public boolean confirmUpload(@RequestParam(name = "key") String key) {
//        Integer point = key.lastIndexOf('.');
//        String idstr = key.substring(0, point);
//        Long id = Long.getLong(idstr);
//        DataFile dataFile = dataFileRepository.getUserDatasetsByPage(id);
//        if (dataFile != null) {
//            dataFile.setStatus(1);
//            dataFileRepository.save(dataFile);
//            return true;
//        } else {
//            return false;
//        }
//    }

    @RequestMapping(value = "/getToken")
    public Map getToken() {
        Map map = new HashMap();
        map.put("uptoken", qiniuService.createUploadToken());
        return map;
    }

    @RequestMapping(value = "/getKey")
    public Map getKey(@RequestParam(name = "name") String name) {
        Map map = new HashMap();

        DataFile dataFile = new DataFile();
        dataFile.setCreateTime(new Date());
        dataFile.setUpdateTime(new Date());
        dataFile.setStatus(0);
        dataFile.setOldname(name);
        dataFileRepository.save(dataFile);

        Integer point = name.lastIndexOf('.');
        String suffix = name.substring(point, name.length());
        String fileName = dataFile.getFileid() + suffix;
        dataFile.setName(fileName);
        dataFileRepository.save(dataFile);

        map.put("name", fileName);
        return map;
    }

    @RequestMapping(value = "/getDownloadUrl")
    public String getDownloadUrl(@RequestParam(name = "fileid") Long fileid) {
        return qiniuService.createDownloadUrl(dataFileRepository.findById(fileid));
    }
}

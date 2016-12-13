package fundata.control;

import com.qiniu.util.Auth;
import fundata.model.DataFile;
import fundata.repository.DataFileRepository;
import fundata.service.QiniuProperties;
import fundata.service.QiniuService;
import fundata.viewmodel.UpFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public UpFileInfo getUploadCSV() {
        DataFile dataFile = new DataFile();
        dataFile.setCreateTime(new Date());
        dataFile.setUpdateTime(new Date());
        dataFile.setStatus(-1);
        dataFile.setSuffix("csv");
        dataFileRepository.save(dataFile);
        String fileName = dataFile.getFileName();
        return new UpFileInfo(qiniuService.createUploadToken(fileName), fileName);
    }

//    @RequestMapping(value = "/")
//    public
}

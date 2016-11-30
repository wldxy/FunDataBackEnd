package fundata.control;

import com.qiniu.util.Auth;
import fundata.service.QiniuProperties;
import fundata.service.QiniuService;
import fundata.viewmodel.UpFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ocean on 16-11-29.
 */
@RestController
public class FileController {
    @Autowired
    @Qualifier("qiniuServiceImpl")
    private QiniuService qiniuService;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public UpFileInfo getUpload() {
        return new UpFileInfo(qiniuService.createUploadToken(), "key.txt");
    }
}

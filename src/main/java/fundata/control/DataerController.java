package fundata.control;

import fundata.configure.Constants;
import fundata.model.DataFile;
import fundata.model.Dataer;
import fundata.repository.DataFileRepository;
import fundata.service.DataerService;
import fundata.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ocean on 16-11-24.
 */
@RestController
@RequestMapping(value = "/dataer")
public class DataerController {

    @Autowired
    DataerService dataerService;

    @Autowired
    DataFileRepository dataFileRepository;

    @Autowired
    QiniuService qiniuService;

    @RequestMapping(value = "/editinfo", method = RequestMethod.GET)
    public Map<String, String> editInfo(@RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId,
                                        @RequestParam(value = "name") String name) {
        Map<String, String> map = new HashMap<>();
        map.put("code", dataerService.editUserInfo(userId, name, null, null) ? "200" : "-1");
        return map;
    }



    // 修改密码
    @ResponseBody
    @RequestMapping(value = "/changepwd", method = RequestMethod.POST)
    public Map<String, String> editProfile(@RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId,
                               @RequestParam String oldpwd,
                               @RequestParam String newpwd){
        Dataer dataer = dataerService.getUserById(userId);
        Map<String, String> map = new HashMap<>();
        if (!dataer.getPassword().equals(oldpwd)) {
            map.put("code", "100");
        }
        else {
            map.put("code", dataerService.editUserInfo(userId, null, newpwd, null) ? "200" : "-1");
        }
        return map;
    }

    // 修改头像
    @ResponseBody
    @RequestMapping(value = "/changeimg",method = RequestMethod.POST)
    public Map<String, String> confirmImg(@RequestParam(name = "key") String key,
                          @RequestAttribute(value = Constants.CURRENT_USER_ID) Long userId){
        Long fileId = Long.parseLong(key.substring(0, key.lastIndexOf('.')));
        DataFile dataFile = dataFileRepository.findById(fileId);

        Map<String, String> map = new HashMap<>();

        if (dataFile == null) {
            map.put("code", "100");
            return map;
        }

        Dataer dataer = dataerService.getUserById(userId);
        if (dataer == null) {
            map.put("code", "150");
            return map;
        }

        String url = qiniuService.createDownloadUrl(dataFile);

        dataerService.editUserInfo(userId, null, null, url);

        map.put("code", "200");
        map.put("url", url);

        return map;

    }

}
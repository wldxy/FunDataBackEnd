package fundata.control;

import fundata.model.DataFile;
import fundata.model.Dataer;
import fundata.repository.DataFileRepository;
import fundata.repository.DataerRepository;
import fundata.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ocean on 16-11-24.
 */
@RestController
public class DataerController {
    @Autowired
    DataerRepository dataerRepository;

    @Autowired
    DataFileRepository dataFileRepository;

    @Autowired
    QiniuService qiniuService;

    @RequestMapping("/authorize/register")
    public Map<String, String> add(@RequestParam(name = "email") String email,
                                   @RequestParam(name = "name") String name,
                                   @RequestParam(name = "pwd") String pwd) {
        Map<String, String> map = new HashMap<>();
        Dataer dataer1 = dataerRepository.findByUserEmail(email);
        Dataer dataer2 = dataerRepository.findByUserName(name);
        if (dataer1 == null && dataer2 == null) {
            Dataer dataer = new Dataer();
            dataer.setEmail(email);
            dataer.setPassword(pwd);
            dataer.setName(name);
            dataer.setHead_href("http://img1.3lian.com/gif/more/11/201209/905adae6a744ae04f0c9ceaceb72d672.jpg");
            dataerRepository.save(dataer);
            map.put("username", name);
        } else {
            map.put("username", "");
        }
        return map;
    }

    @RequestMapping(value = "/authorize/login", method = RequestMethod.POST)
    public Map<String, String> checkLoginByName(@RequestParam(value = "email") String email,
                                                @RequestParam(value = "pwd") String password) {
        Dataer user = dataerRepository.findByUserEmail(email);
        Map<String, String> map = new HashMap<>();
        if (user == null) {
            map.put("username", "");
        }
        if (user.getPassword().equals(password)) {
            map.put("username", user.getName());
        } else {
            map.put("username", "");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/authorize/user", method = RequestMethod.POST)
    public Map getUserProfile(@RequestParam String username){
        try{
            Dataer dataer = dataerRepository.findByUserName(username);

            Map map = new HashMap<>();
            map.put("user_id", dataer.getId());
            map.put("user_name", dataer.getName());
            map.put("user_email", dataer.getEmail());
            map.put("head_href", dataer.getHead_href());

            return map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 修改密码
    @ResponseBody
    @RequestMapping(value = "/authorize/edit", method = RequestMethod.POST)
    public boolean editProfile(@RequestParam String username,
                               @RequestParam String oldpwd,
                               @RequestParam String newpwd){
        try{
            Dataer dataer = dataerRepository.findByUserName(username);
            if (dataer.getPassword().equals(oldpwd)){
                dataer.setPassword(newpwd);
                dataerRepository.save(dataer);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // 修改头像
    @ResponseBody
    @RequestMapping(value = "/authorize/confirmImg",method = RequestMethod.POST)
    public Map confirmImg(@RequestParam(name = "key") String key,
                          @RequestParam(name = "username") String username){
        System.out.println("===============");
        System.out.println("Img  "+key+" is confirmed");
        System.out.println("===============");

        Long fileid = Long.parseLong(key.substring(0, key.lastIndexOf('.')));
        DataFile dataFile = dataFileRepository.findById(fileid);

        Map map = new HashMap<>();

        if (dataFile == null) {
            map.put("flag", false);
            return map;
        }

        Dataer dataer = dataerRepository.findByUserName(username);
        if (dataer == null) {
            map.put("flag", false);
            return map;
        }

        String url = qiniuService.createDownloadUrl(dataFile);

        dataer.setHead_href(url);
        dataerRepository.save(dataer);

        map.put("flag", true);
        map.put("url", url);

        return map;

    }

}
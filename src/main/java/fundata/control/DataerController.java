package fundata.control;

import fundata.model.Dataer;
import fundata.model.PullRequest;
import fundata.repository.DataerRepository;
import fundata.viewmodel.HotProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ocean on 16-11-24.
 */
@RestController
public class DataerController {
    @Autowired
    DataerRepository dataerRepository;

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
}
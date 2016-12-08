package fundata.control;

import fundata.model.Dataer;
import fundata.repository.DataerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ocean on 16-11-24.
 */
@RestController
public class DataerController {
    @Autowired
    DataerRepository dataerRepository;

    @RequestMapping("/authorize/register")
    public boolean add(@RequestParam(name = "email") String email,
                       @RequestParam(name = "name") String name,
                       @RequestParam(name = "pwd") String pwd) {
        Dataer dataer1 = dataerRepository.findByUserEmail(email);
        Dataer dataer2 = dataerRepository.findByUserName(name);
        if (dataer1 == null && dataer2 == null) {
            Dataer dataer = new Dataer();
            dataer.setEmail(email);
            dataer.setPassword(pwd);
            dataer.setName(name);
            dataerRepository.save(dataer);
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping(value = "/authorize/login", method = RequestMethod.POST)
    public Integer checkLoginByName(@RequestParam(value = "user") String name,
                                    @RequestParam(value = "password") String password) {
        Dataer user = dataerRepository.findByUserName(name);
        if (user == null) {
            return -1;
        }
        if (user.getPassword().equals(password)) {
            return 1;
        } else {
            return 0;
        }
    }
}
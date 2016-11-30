package fundata.control;

//import repository.UserRepository;
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
    DataerRepository repository;

    @RequestMapping("/add")
    public String add() {
        Dataer user = new Dataer();
        user.setName("nana");
        user.setPassword("123456");
        user.setEmail("hahah@163.com");
        repository.save(user);
        return "Add User";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String check(@RequestParam(value = "user") String name,
                        @RequestParam(value = "password") String password) {
        Dataer user = repository.findByUserName(name);
        if (user.getPassword().equals(password)) {
            return "login success";
        } else {
            return "login fail";
        }
    }
}
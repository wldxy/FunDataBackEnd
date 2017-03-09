package fundata.control;

import fundata.annotation.CurrentUser;
import fundata.model.Dataer;
import fundata.model.Token;
import fundata.repository.TokenRepository;
import fundata.service.DataerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huang on 17-3-9.
 */
@RestController
@RequestMapping(value = "/authorize")
public class AuthorizeController {

    @Autowired
    DataerService dataerService;

    @Autowired
    TokenRepository tokenRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Map<String, String> login(@RequestParam(value = "email") String email,
                                     @RequestParam(value = "pwd") String password) {
        Dataer user = dataerService.findByEmail(email);
        Map<String, String> map = new HashMap<>();
        if (user == null || !user.getPassword().equals(password)) {
            map.put("token", "");
        }
        Token token= tokenRepository.createToken(user.getId());
        map.put("token", token.getToken());
        map.put("userId", user.getId().toString());
        return map;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Map<String, String> logOut(@CurrentUser Dataer user) {
        tokenRepository.deleteToken(user.getId());
        Map<String, String> map = new HashMap();
        map.put("status", "OK");
        return map;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Map<String, String> add(@RequestParam(name = "email") String email,
                                   @RequestParam(name = "name") String name,
                                   @RequestParam(name = "pwd") String pwd) {
        Map<String, String> map = new HashMap<>();
        Dataer dataer1 = dataerService.findByEmail(email);
        Dataer dataer2 = dataerService.findByDataerName(name);
        if (dataer1 == null && dataer2 == null) {
            Dataer dataer = new Dataer();
            dataer.setEmail(email);
            dataer.setPassword(pwd);
            dataer.setName(name);
            dataer.setHead_href("http://img1.3lian.com/gif/more/11/201209/905adae6a744ae04f0c9ceaceb72d672.jpg");
            dataerService.save(dataer);
            map.put("username", name);
        } else {
            map.put("username", "");
        }
        return map;
    }
}

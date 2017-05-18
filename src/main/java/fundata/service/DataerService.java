package fundata.service;

import fundata.model.Competition;
import fundata.model.Dataer;

import java.util.Set;


/**
 * Created by stanforxc on 2016/12/5.
 */
public interface DataerService {
    void save(Dataer dataer);

    boolean editUserInfo(Long id, String newName, String newPwd, String newUrl);

    Dataer getUserById(Long id);

    Dataer findByDataerName(String dataerName);

    Dataer findByEmail(String email);

    Set<Dataer> findLikeName(String dataerName);
}

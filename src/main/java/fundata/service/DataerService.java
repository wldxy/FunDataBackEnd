package fundata.service;

import fundata.model.Dataer;


/**
 * Created by stanforxc on 2016/12/5.
 */
public interface DataerService {
    void save(Dataer dataer);
    Dataer findById(Long id);
}

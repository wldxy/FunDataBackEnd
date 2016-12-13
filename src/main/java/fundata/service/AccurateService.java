package fundata.service;

import fundata.model.Accurate;
import fundata.model.Dataer;

import java.util.Set;

/**
 * Created by stanforxc on 2016/12/13.
 */
public interface AccurateService {
    boolean save(Accurate accurate);
    Set<Accurate> findByDataer(Dataer dataer);
}

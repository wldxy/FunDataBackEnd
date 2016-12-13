package fundata.service;

import fundata.model.Accurate;
import fundata.model.Dataer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * Created by stanforxc on 2016/12/13.
 */
public interface AccurateService {
    boolean save(Accurate accurate);
    Set<Accurate> findByDataer(Dataer dataer);
    Page<Accurate> findAll(Pageable pageable);
}

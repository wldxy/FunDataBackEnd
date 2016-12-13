package fundata.repository;

import ch.qos.logback.core.joran.action.ActionUtil;
import fundata.model.Accurate;
import fundata.model.Dataer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * Created by stanforxc on 2016/12/13.
 */
@Repository
public interface AccurateRepository extends JpaRepository<Accurate,Long>{
    Set<Accurate> findByDataer(Dataer dataer);
}

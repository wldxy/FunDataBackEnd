package fundata.repository;

import fundata.model.Competition;
import fundata.model.Dataer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by stanforxc on 2016/12/12.
 */
@Repository
public interface CompetitionRepostiory extends JpaRepository<Competition,Long>{
    Competition findById(Long comId);
}

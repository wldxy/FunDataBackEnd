package fundata.service;

import fundata.model.Competition;
import fundata.model.Dataer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Created by stanforxc on 2016/12/12.
 */
public interface CompetitionService {
    void save(Competition competition);

    Competition findById(Long id);


    Set<Competition> findLikeName(String name);
  //  Dataer findHost(Competition competition);
   // Set<Dataer> findContester(Competition competition);
    Page<Competition> findAll(Pageable pageable);

    void deleteCompetition(Competition competition);
}

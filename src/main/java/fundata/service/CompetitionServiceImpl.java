package fundata.service;

import fundata.model.Competition;
import fundata.model.Dataer;
import fundata.repository.CompetitionRepostiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Set;

/**
 * Created by stanforxc on 2016/12/12.
 */
@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    CompetitionRepostiory competitionRepostiory;

    @Override
    public void save(Competition competition) {
        competitionRepostiory.save(competition);
    }

    @Override
    public Competition findById(Long id) {
        return competitionRepostiory.findById(id);
    }

   // @Override
   // public Dataer findHost(Competition competition) {
      //  return competitionRepostiory.findByDataer(competition);
 //   }

   /* @Override
    public Set<Dataer> findContester(Competition competition) {
        return competitionRepostiory.findByDataers(competition);
    }*/

    @Override
    public Page<Competition> findAll(Pageable pageable) {
        return competitionRepostiory.findAll(pageable);
    }
}

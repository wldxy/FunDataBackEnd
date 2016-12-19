package fundata.repository;

import fundata.model.Competition;
import fundata.model.Dataer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * Created by ocean on 16-11-24.
 */
public interface DataerRepository extends JpaRepository<Dataer, Long>, JpaSpecificationExecutor<Dataer> {
    @Query("select u from dataer u where u.name = :um")
    public Dataer findByUserName(@Param("um") String name);

    @Query("select u from dataer u where u.email = :um")
    public Dataer findByUserEmail(@Param("um") String name);

    Dataer findByHostcompetition(Competition competition);

    @Query("select u from dataer u where u.name like ?1")

    Set<Dataer> findLikeName(String name);
    Dataer findById(Long id);

}
package fundata.repository;

import fundata.model.Dataer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by ocean on 16-11-24.
 */
public interface DataerRepository extends JpaRepository<Dataer, Long> {
    @Query("select u from dataer u where u.name = :um")
    public Dataer findByUserName(@Param("um") String name);

}
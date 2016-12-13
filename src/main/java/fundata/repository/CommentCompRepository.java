package fundata.repository;

import fundata.model.Commentcomp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by stanforxc on 2016/12/12.
 */
@Repository
public interface CommentCompRepository extends JpaRepository<Commentcomp,Long>{
}

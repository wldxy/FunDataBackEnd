package fundata.repository;

import fundata.model.PullRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by ocean on 16-12-7.
 */
public interface PullRequestRepository
        extends JpaRepository<PullRequest, Long>, JpaSpecificationExecutor<PullRequest> {

}

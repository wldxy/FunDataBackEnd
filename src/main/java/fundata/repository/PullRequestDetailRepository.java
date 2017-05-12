package fundata.repository;

import fundata.document.PullRequestDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by huang on 17-4-19.
 */
public interface PullRequestDetailRepository extends MongoRepository<PullRequestDetail, Long> {
    PullRequestDetail findByPullRequestId(Long pullRequestId);
}

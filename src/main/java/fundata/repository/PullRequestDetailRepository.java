package fundata.repository;

import fundata.document.PullRequestStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by huang on 17-4-19.
 */
public interface PullRequestDetailRepository extends MongoRepository<PullRequestStatistics, Long> {
    PullRequestStatistics findByPullRequestId(Long pullRequestId);
}

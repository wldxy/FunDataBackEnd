package fundata.message;

import fundata.model.PullRequest;
import fundata.repository.PullRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by ocean on 17-4-26.
 */
@Component
public class Receiver {
    @Autowired
    private PullRequestRepository pullRequestRepository;

    @JmsListener(destination = "mergeresult.queue")
    public void receiveMessage(ResultMessage result) {
        PullRequest pullRequest = pullRequestRepository.findOne(result.getPullrequest_id());
        pullRequest.setStatus((short) 1);
        pullRequestRepository.save(pullRequest);
    }

}

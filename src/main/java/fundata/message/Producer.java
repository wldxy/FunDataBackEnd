package fundata.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * Created by ocean on 17-4-29.
 */
@Component
//@EnableScheduling
public class Producer {
    @Autowired
    private JmsTemplate jmsTemplate;

//    public void send(SprayE)
    @Autowired
    @Qualifier("pullrequest")
    private Queue queue;

//    @Scheduled(fixedDelay=3000)//每3s执行1次
    public void send(PullRequestMessage pullRequestMessage) {
        this.jmsTemplate.convertAndSend(this.queue, pullRequestMessage);
    }

}

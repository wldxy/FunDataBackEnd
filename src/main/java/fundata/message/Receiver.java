package fundata.message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by ocean on 17-4-26.
 */
@Component
public class Receiver {

    @JmsListener(destination = "sample.queue")
    public void receiveMessage(String text) {
        System.out.println(text);
    }

}

package im.baas.sandbox.ignite.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteMessaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * Created by stliu on 8/19/16.
 */
@RestController
@RequestMapping(path = "/message")
@Slf4j
public class MessageController {
    @Autowired
    private Ignite ignite;
    private String topic = "message";
    IgniteMessaging messaging;

    @PostConstruct
    public void init() {
        messaging = ignite.message();
        messaging.withAsync().localListen(topic, new LocalIgniteMessageListener());
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void send(@RequestBody String msg) {
        log.info("sending message [{}] to topic {}", msg, topic);
        messaging.sendOrdered(topic, msg, 1);
    }
}

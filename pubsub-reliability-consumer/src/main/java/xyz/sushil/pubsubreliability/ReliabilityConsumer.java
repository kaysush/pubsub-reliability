package xyz.sushil.pubsubreliability;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.pubsub.v1.PubsubMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReliabilityConsumer implements Runnable {

  @Value("${pubsub.region}")
  private String region;

  @Value("${pubsub.subscription}")
  private String subscription;

  @Autowired
  private PubSubTemplate pubSubTemplate;


  @Override
  public void run() {
    log.info("Starting consumer in region {}",region);

    Subscriber subscriber = pubSubTemplate.subscribe(subscription, (basicAcknowledgeablePubsubMessage -> {
      PubsubMessage message = basicAcknowledgeablePubsubMessage.getPubsubMessage();
      String data = message.getData().toStringUtf8();
      String id = message.getMessageId();
      log.info("Sub Region : {} , Message {}", region, data);
      basicAcknowledgeablePubsubMessage.ack();
    }));
    subscriber.startAsync();

  }
}

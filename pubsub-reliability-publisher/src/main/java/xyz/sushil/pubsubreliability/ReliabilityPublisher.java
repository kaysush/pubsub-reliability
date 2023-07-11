package xyz.sushil.pubsubreliability;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReliabilityPublisher implements Runnable {

  @Value("${pubsub.region}")
  private String region;

  @Value("${pubsub.topic}")
  private String topic;

  @Autowired
  private PubSubTemplate template;


  @Override
  public void run() {
    log.info("Starting publisher in region {} for topic {}", region, topic);

    while (!Thread.currentThread().isInterrupted()) {
      log.debug("Lived for one more cycle ;)");


      try {

        long startMillis = Instant.now().toEpochMilli();
        int num = (int) (Math.random() * 100);
        String message = String.format("Message from region : %s with number %d", region, num);
        String id = template.publish(topic, message).get(5, TimeUnit.SECONDS);
        log.info("Published a message with id {}", id);
        long endMillis = Instant.now().toEpochMilli();
        long diff = 2000 - (endMillis - startMillis);

        Thread.sleep(diff);
      } catch (InterruptedException ex) {
        log.error("Thread interrupted. Stopping the publisher.");
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      } catch (TimeoutException e) {
        throw new RuntimeException(e);
      }

    }


  }
}

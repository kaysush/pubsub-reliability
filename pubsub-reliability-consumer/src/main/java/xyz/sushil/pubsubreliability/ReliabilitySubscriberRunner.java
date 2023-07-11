package xyz.sushil.pubsubreliability;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReliabilitySubscriberRunner implements CommandLineRunner {

  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  @Autowired
  private final ReliabilityConsumer consumer;

  @Override
  public void run(String... args) throws Exception {
    executor.submit(consumer);
  }
}

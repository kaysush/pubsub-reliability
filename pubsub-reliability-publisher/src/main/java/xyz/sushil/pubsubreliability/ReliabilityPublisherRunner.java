package xyz.sushil.pubsubreliability;

import java.util.concurrent.ThreadPoolExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
@RequiredArgsConstructor
public class ReliabilityPublisherRunner implements CommandLineRunner {

  private final ReliabilityPublisher publisher;

  @Override
  @Async
  public void run(String... args) throws Exception {
    publisher.run();
  }
}

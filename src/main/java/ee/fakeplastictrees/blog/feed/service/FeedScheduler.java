package ee.fakeplastictrees.blog.feed.service;

import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FeedScheduler {
  private final FeedService feedService;

  public FeedScheduler(FeedService feedService) {
    this.feedService = feedService;
  }

  // todo: move params to env vars
  @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
  public void updateMastodonFeed() {
    feedService.updateMastodonFeed();
  }
}

package ee.fakeplastictrees.blog.feed.service;

import ee.fakeplastictrees.blog.feed.model.FeedEntryDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeedService {
  private final MastodonClient mastodonClient;

  // wow, much database, so cool! todo
  private static List<FeedEntryDto> entries;

  @Value("${media.feed.mastodon.profile}")
  private String mastodonProfile;

  public FeedService(MastodonClient mastodonClient) {
    this.mastodonClient = mastodonClient;
  }

  public void updateMastodonFeed() {
    entries =
        mastodonClient.getPosts(mastodonProfile).stream()
            .map((post) -> new FeedEntryDto(post.getDescription().getValue()))
            .toList();
    // todo
    System.out.println("done");
  }

  public List<FeedEntryDto> getMastodonPosts() {
    return entries;
  }
}

package ee.fakeplastictrees.blog.feed.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeedService {
  private final MastodonClient mastodonClient;

  private static List<String> entries;

  @Value("${media.feed.mastodon.profile}")
  private String mastodonProfile;

  public FeedService(MastodonClient mastodonClient) {
    this.mastodonClient = mastodonClient;
  }

  public void updateMastodonFeed() {
    entries = mastodonClient.fetchPostLinksByUserProfile(mastodonProfile);
    entries.forEach(System.out::println);
  }

  public List<String> getMastodonPosts() {
    return entries;
  }
}

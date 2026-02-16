package ee.fakeplastictrees.blog.feed.service;

import static java.lang.String.format;

import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MastodonClient {
  private final HttpClient client;

  public MastodonClient() {
    this.client = HttpClient.newHttpClient();
  }

  public List<String> fetchRssFeedByUsername(String username) {
    try {
      // todo: move host to env vars
      var uri = new URI(format("https://mastodon.ie/@%s.rss", username));
      var request = HttpRequest.newBuilder().uri(uri).build();

      var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
      var feed = new SyndFeedInput().build(new XmlReader(response.body()));

      return feed.getEntries().stream().map(entry -> entry.getLink()).toList();
    } catch (Exception e) {
      // todo: use proper logger
      e.printStackTrace();
      return List.of();
    }
  }
}

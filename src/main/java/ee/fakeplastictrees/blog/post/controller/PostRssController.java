package ee.fakeplastictrees.blog.post.controller;

import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import ee.fakeplastictrees.blog.post.model.PostDto;
import ee.fakeplastictrees.blog.post.service.PostService;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feed.xml")
public class PostRssController {
  private final PostService postService;

  @Value("${posts.rss.page.size:20}")
  private Integer pageSize;

  @Value("${posts.rss.feed.title}")
  private String rssFeedTitle;

  @Value("${posts.rss.feed.description}")
  private String rssFeedDescription;

  @Value("${posts.rss.feed.website.link}")
  private String rssFeedWebsiteLink;

  @Value("${posts.rss.feed.website.link.format}")
  private String rssFeedLinkFormat;

  public PostRssController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping(produces = MediaType.APPLICATION_RSS_XML_VALUE)
  public ResponseEntity<String> getRssFeed() throws FeedException {
    var entries =
        postService.getPublishedPostsFull(1, pageSize).posts().stream()
            .map(this::mapPostToEntry)
            .toList();

    var feed = new SyndFeedImpl();
    feed.setFeedType("rss_2.0");
    feed.setTitle(rssFeedTitle);
    feed.setDescription(rssFeedDescription);
    feed.setLink(rssFeedWebsiteLink);
    feed.setEntries(entries);

    return ResponseEntity.ok(new SyndFeedOutput().outputString(feed));
  }

  private SyndEntry mapPostToEntry(PostDto post) {
    var text = post.text();
    if (text == null || text.isBlank()) {
      text = post.summary();
    }

    var parsedSummary = Parser.builder().build().parse(text);
    var htmlSummary = HtmlRenderer.builder().build().render(parsedSummary);

    var description = new SyndContentImpl();
    description.setValue(htmlSummary);

    var entry = new SyndEntryImpl();
    entry.setUri(post.id());
    entry.setTitle(post.title());
    entry.setDescription(description);
    entry.setLink(String.format(rssFeedLinkFormat, post.id()));

    return entry;
  }
}

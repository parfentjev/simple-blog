package ee.fakeplastictrees.blog.service.post.controller;

import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.SyndFeedOutput;
import ee.fakeplastictrees.blog.codegen.model.PostPreviewDto;
import ee.fakeplastictrees.blog.service.post.service.PostService;
import lombok.SneakyThrows;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rss/posts")
public class PostRssController {
  @Autowired
  private PostService postService;

  @Value("${rss.feed.title}")
  private String rssFeedTitle;

  @Value("${rss.feed.description}")
  private String rssFeedDescription;

  @Value("${rss.feed.website.link}")
  private String rssFeedWebsiteLink;

  @Value("${rss.feed.website.link.format}")
  private String rssFeedLinkFormat;

  @SneakyThrows
  @GetMapping(produces = MediaType.APPLICATION_RSS_XML_VALUE)
  public ResponseEntity<String> getRssFeed() {
    var entries = postService.getPosts(1)
      .getItems()
      .stream()
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

  private SyndEntry mapPostToEntry(PostPreviewDto post) {
    var parsedSummary = Parser.builder().build().parse(post.getSummary());
    var htmlSummary = HtmlRenderer.builder().build().render(parsedSummary);

    var description = new SyndContentImpl();
    description.setValue(htmlSummary);

    var entry = new SyndEntryImpl();
    entry.setUri(post.getId());
    entry.setTitle(post.getTitle());
    entry.setDescription(description);
    entry.setLink(String.format(rssFeedLinkFormat, post.getId()));

    return entry;
  }
}

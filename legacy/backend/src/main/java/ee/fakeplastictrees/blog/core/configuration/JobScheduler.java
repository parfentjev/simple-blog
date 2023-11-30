package ee.fakeplastictrees.blog.core.configuration;

import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.SyndFeedOutput;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;
import ee.fakeplastictrees.blog.post.service.PostService;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.FileWriter;
import java.util.List;

import static java.lang.String.format;

@Configuration
@EnableScheduling
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobScheduler {
    final Logger logger = LoggerFactory.getLogger(JobScheduler.class);

    @Autowired
    PostService postService;

    @Value("${rss.feed.outputFile}")
    String rssFeedOutputFile;

    @Value("${rss.feed.type}")
    String rssFeedType;

    @Value("${rss.feed.title}")
    String rssFeedTitle;

    @Value("${rss.feed.description}")
    String rssFeedDescription;

    @Value("${rss.feed.websiteLink}")
    String rssFeedWebsiteLink;

    @Value("${rss.feed.linkFormat}")
    String rssFeedLinkFormat;

    @Value("${rss.feed.items}")
    Integer rssFeedItems;

    @SneakyThrows
    @Scheduled(cron = "${rss.feed.cronExpression}")
    public void generateRssFeed() {
        List<SyndEntry> entries = postService.getPosts(1, rssFeedItems)
                .getItems()
                .stream()
                .map(this::mapPostToEntry)
                .toList();

        SyndFeedImpl feed = new SyndFeedImpl();
        feed.setFeedType(rssFeedType);
        feed.setTitle(rssFeedTitle);
        feed.setDescription(rssFeedDescription);
        feed.setLink(rssFeedWebsiteLink);
        feed.setEntries(entries);

        try (FileWriter writer = new FileWriter(rssFeedOutputFile)) {
            new SyndFeedOutput().output(feed, writer, false);
        }

        logger.info("Updated the RSS feed: " + rssFeedOutputFile);
    }

    private SyndEntry mapPostToEntry(PostPreviewDto post) {
        Node parsedSummary = Parser.builder().build().parse(post.getSummary());
        String htmlSummary = HtmlRenderer.builder().build().render(parsedSummary);

        SyndContentImpl description = new SyndContentImpl();
        description.setValue(htmlSummary);

        SyndEntry entry = new SyndEntryImpl();
        entry.setUri(post.getId());
        entry.setTitle(post.getTitle());
        entry.setDescription(description);
        entry.setLink(format(rssFeedLinkFormat, post.getId()));

        return entry;
    }
}

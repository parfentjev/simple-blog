package ee.fakeplastictrees.blog.core.configuration;

import ee.fakeplastictrees.blog.core.model.PageDto;
import ee.fakeplastictrees.blog.post.model.PostPreviewDto;
import ee.fakeplastictrees.blog.post.service.PostService;
import ee.fakeplastictrees.blog.testsupport.AbstractTest;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static ee.fakeplastictrees.blog.core.Utils.builders;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobSchedulerTest extends AbstractTest {
    @Mock
    PostService postService;

    @InjectMocks
    final JobScheduler jobScheduler = new JobScheduler();

    final String rssFeedOutputFile = "build/feed.xml";
    final String rssFeedTitle = "Fake Plastic Tests";
    final String rssFeedDescription = "Contains green plastic watering cans.";
    final String rssFeedWebsiteLink = "https://www.website.com";
    final Integer rssFeedItems = 20;

    @BeforeEach
    public void beforeEach() {
        ReflectionTestUtils.setField(jobScheduler, "rssFeedOutputFile", rssFeedOutputFile);
        ReflectionTestUtils.setField(jobScheduler, "rssFeedType", "rss_2.0");
        ReflectionTestUtils.setField(jobScheduler, "rssFeedTitle", rssFeedTitle);
        ReflectionTestUtils.setField(jobScheduler, "rssFeedDescription", rssFeedDescription);
        ReflectionTestUtils.setField(jobScheduler, "rssFeedWebsiteLink", rssFeedWebsiteLink);
        ReflectionTestUtils.setField(jobScheduler, "rssFeedLinkFormat", rssFeedWebsiteLink + "/post/%s/read");
        ReflectionTestUtils.setField(jobScheduler, "rssFeedItems", rssFeedItems);
    }

    @Test
    public void testRssFeedGenerator() {
        List<PostPreviewDto> items = IntStream.rangeClosed(0, 1)
                .mapToObj(i -> builders().post().postPreviewDto()
                        .id(String.valueOf(i))
                        .title(format("Post #%d", i))
                        .summary(format("Summary #%d", i))
                        .date(LocalDateTime.now().plusDays(i).toString())
                        .categories(Collections.emptyList())
                        .build())
                .toList();

        PageDto<PostPreviewDto> pageDto = builders().<PostPreviewDto>pageDto()
                .page(1)
                .totalPages(1)
                .items(items)
                .build();

        when(postService.getPosts(1, rssFeedItems)).thenReturn(pageDto);
        jobScheduler.generateRssFeed();

        String expectedOutput = getExpectedOutput();
        String actualOutput = getActualOutput();
        assertThat(expectedOutput).isEqualToNormalizingNewlines(actualOutput);
    }

    private String getExpectedOutput() {
        return format("""
                        <?xml version="1.0" encoding="UTF-8"?>
                        <rss version="2.0"><channel><title>%s</title><link>%s</link><description>%s</description><item><title>Post #0</title><link>%s/post/0/read</link><description>Summary #0</description><guid isPermaLink="false">0</guid></item><item><title>Post #1</title><link>%s/post/1/read</link><description>Summary #1</description><guid isPermaLink="false">1</guid></item></channel></rss>
                        """,
                rssFeedTitle,
                rssFeedWebsiteLink,
                rssFeedDescription,
                rssFeedWebsiteLink,
                rssFeedWebsiteLink);
    }

    @SneakyThrows
    private String getActualOutput() {
        return Files.readString(Path.of(rssFeedOutputFile));
    }
}
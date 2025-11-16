package ee.fakeplastictrees.blog.core.markdown;

import java.util.Set;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

@Component
public class MarkdownDialect extends AbstractProcessorDialect {
  private static final String PREFIX = "markdown";
  private static final Integer PRECEDENCE = 1000;

  protected MarkdownDialect() {
    super("Markdown Dialect", PREFIX, PRECEDENCE);
  }

  @Override
  public Set<IProcessor> getProcessors(String dialectPrefix) {
    return Set.of(new MarkdownProcessor(PREFIX, PRECEDENCE));
  }
}

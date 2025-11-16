package ee.fakeplastictrees.blog.core.markdown;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

public class MarkdownProcessor extends AbstractAttributeTagProcessor {
  protected MarkdownProcessor(String prefix, Integer precedence) {
    super(TemplateMode.HTML, prefix, null, false, "toHtml", true, precedence, true);
  }

  @Override
  protected void doProcess(
      ITemplateContext context,
      IProcessableElementTag tag,
      AttributeName attributeName,
      String attributeValue,
      IElementTagStructureHandler structureHandler) {
    var content =
        (String)
            StandardExpressions.getExpressionParser(context.getConfiguration())
                .parseExpression(context, attributeValue)
                .execute(context);

    structureHandler.setBody(markdownToHtml(content), false);
  }

  private String markdownToHtml(String markdown) {
    var document = Parser.builder().build().parse(markdown);
    var renderer =
        HtmlRenderer.builder()
            .attributeProviderFactory(LinkAttributeProvider::new)
            .nodeRendererFactory(ImageNodeRenderer::new)
            .build();

    return renderer.render(document);
  }
}

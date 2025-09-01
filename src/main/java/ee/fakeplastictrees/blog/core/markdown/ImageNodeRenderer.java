package ee.fakeplastictrees.blog.core.markdown;

import org.commonmark.node.Image;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;

import java.util.Map;
import java.util.Set;

public class ImageNodeRenderer implements NodeRenderer {
  private final HtmlWriter writer;

  public ImageNodeRenderer(HtmlNodeRendererContext context) {
    this.writer = context.getWriter();
  }

  @Override
  public Set<Class<? extends Node>> getNodeTypes() {
    return Set.of(Image.class);
  }

  @Override
  public void render(Node node) {
    var imageNode = (Image) node;
    var text = "";

    var source = imageNode.getDestination();
    if (source == null) return;

    var child = imageNode.getFirstChild();
    if (child instanceof Text && ((Text) child).getLiteral() != null) {
      text = ((Text) child).getLiteral();
    }

    var anchorAttributes = Map.of("href", source, "target", "_blank", "rel", "noopener");
    var imageAttributes = Map.of("src", source, "alt", text, "title", text);

    writer.tag("a", anchorAttributes);
    writer.tag("img", imageAttributes);
    writer.tag("/a");
  }
}

package ee.fakeplastictrees.blog.core.markdown;

import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;

import java.util.Map;

public class LinkAttributeProvider implements AttributeProvider {
  public LinkAttributeProvider(AttributeProviderContext context) {
  }

  @Override
  public void setAttributes(Node node, String s, Map<String, String> map) {
    if (node instanceof Link) {
      map.put("target", "_blank");
      map.put("rel", "noopener");
    }
  }
}

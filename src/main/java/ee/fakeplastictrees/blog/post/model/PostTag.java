package ee.fakeplastictrees.blog.post.model;

import ee.fakeplastictrees.blog.post.model.PostTag.PostTagId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import java.io.Serializable;

// https://www.baeldung.com/jpa-composite-primary-keys#bd-idclass
@Entity(name = "post_tags")
@IdClass(PostTagId.class)
public class PostTag {
  @Id private String postId;

  @Id private String tagId;

  public String getPostId() {
    return postId;
  }

  public void setPostId(String postId) {
    this.postId = postId;
  }

  public String getTagId() {
    return tagId;
  }

  public void setTagId(String tagId) {
    this.tagId = tagId;
  }

  public static class PostTagId implements Serializable {
    private String postId;

    private String tagId;
  }
}

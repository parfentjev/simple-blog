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

    // hashCode and equals aren't strictly necessary
    // (the code compiles and works without them)
    // but seemingly the lack of them can lead to unpredictable behavior

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((postId == null) ? 0 : postId.hashCode());
      result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      PostTagId other = (PostTagId) obj;
      if (postId == null) {
        if (other.postId != null) return false;
      } else if (!postId.equals(other.postId)) return false;
      if (tagId == null) {
        if (other.tagId != null) return false;
      } else if (!tagId.equals(other.tagId)) return false;
      return true;
    }
  }
}

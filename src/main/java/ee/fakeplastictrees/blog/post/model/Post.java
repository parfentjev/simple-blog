package ee.fakeplastictrees.blog.post.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "posts")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String title;

  private String slug;

  private String summary;

  private String text;

  private Instant date;

  private Boolean visible;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Instant getDate() {
    return date;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public Boolean getVisible() {
    return visible;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }
}

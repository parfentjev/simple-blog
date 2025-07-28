package ee.fakeplastictrees.blog.file.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity(name = "files")
public class File {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(unique = true)
  private String id;

  @Column(unique = true)
  private String originalFilename;

  @Column(nullable = false)
  private String path;

  @Column(nullable = false)
  private String contentType;

  @Column(nullable = false)
  private Instant uploadedAt;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOriginalFilename() {
    return originalFilename;
  }

  public void setOriginalFilename(String originalFilename) {
    this.originalFilename = originalFilename;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public Instant getUploadedAt() {
    return uploadedAt;
  }

  public void setUploadedAt(Instant uploadedAt) {
    this.uploadedAt = uploadedAt;
  }
}

package ee.fakeplastictrees.blog.service.media.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Media {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(unique = true)
  String id;

  @Column(unique = true)
  String originalFilename;

  @Column(nullable = false)
  String path;

  @Column(nullable = false)
  String contentType;

  @Column(nullable = false)
  Instant uploadedAt;
}

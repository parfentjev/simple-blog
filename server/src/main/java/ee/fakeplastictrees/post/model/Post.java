package ee.fakeplastictrees.post.model;

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
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(unique = true)
  String id;

  @Column(nullable = false)
  String title;

  String summary;

  String text;

  Instant date;

  @Column(nullable = false)
  Boolean visible;
}

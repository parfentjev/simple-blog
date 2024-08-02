package ee.fakeplastictrees.user.model;

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
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(unique = true)
  String id;

  @Column(nullable = false)
  String username;

  @Column(nullable = false)
  String password;

  @Column(nullable = false)
  Instant createdAt;

  @Column(nullable = false)
  Boolean active;
}

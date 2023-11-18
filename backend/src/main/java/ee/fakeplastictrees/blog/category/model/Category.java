package ee.fakeplastictrees.blog.category.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column
    String name;
}

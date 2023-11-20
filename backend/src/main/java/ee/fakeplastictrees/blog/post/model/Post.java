package ee.fakeplastictrees.blog.post.model;

import ee.fakeplastictrees.blog.category.model.Category;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column
    String title;

    @Column
    String summary;

    @Column
    String text;

    @Column
    String date;

    @Column
    Boolean visible;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Category> categories;
}


package springboot.webtruyen.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;

    // Mối quan hệ ngược lại với Story
    @ManyToMany(mappedBy = "genres")
    private Set<Story> stories;

    // Getters và Setters
}

package springboot.webtruyen.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chapters")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "chapter_number")
    private int chapterNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

    // Getters và Setters
}

package springboot.webtruyen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.webtruyen.model.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {
    Story findBySlug(String slug);
}

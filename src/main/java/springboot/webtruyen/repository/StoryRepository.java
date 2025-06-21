package springboot.webtruyen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.webtruyen.model.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface StoryRepository extends JpaRepository<Story, Long> {
    Story findBySlug(String slug);
    Page<Story> findAll(Pageable pageable);
}

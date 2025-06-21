package springboot.webtruyen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.webtruyen.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}



package at.davl.movie.repository;

import at.davl.movie.models.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {
}
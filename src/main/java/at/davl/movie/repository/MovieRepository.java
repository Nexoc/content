package at.davl.movie.repository;

import at.davl.movie.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie, Integer> {}

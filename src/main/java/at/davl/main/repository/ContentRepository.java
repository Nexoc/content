package at.davl.main.repository;

import at.davl.main.models.Content;
import at.davl.main.models.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {

    // todo to check
    List<Content> findAllContentsByFolder(Folder folder);

}
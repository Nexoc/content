package at.davl.main.repository;

import at.davl.main.models.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {
}
package at.davl.main.repository;

import at.davl.main.auth.entities.User;
import at.davl.main.models.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

    @Query("SELECT f FROM Folder f WHERE f.userId = :userId")
    List<Folder> findAllFoldersByUserId(Integer userId);
}
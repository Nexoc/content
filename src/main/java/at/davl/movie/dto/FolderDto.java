package at.davl.movie.dto;

import at.davl.movie.auth.entities.User;
import at.davl.movie.models.Content;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderDto {
    private Integer folderId;
    private Integer number;
    private User user;
    private Set<Content> contents;
}

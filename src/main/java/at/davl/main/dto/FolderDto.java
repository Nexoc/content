package at.davl.main.dto;

import at.davl.main.auth.entities.User;
import at.davl.main.models.Content;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderDto {
    private Integer folderId;
    private Integer number;
    @NotBlank(message = "Please provide owner")
    private User user;
    private Set<Content> contents;
}

package at.davl.main.dto;

import at.davl.main.models.Folder;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDto {
    private Integer contentId;
    @NotBlank(message = "Please provide content title")
    private String title;
    @NotBlank(message = "Please provide content")
    private String content;
    private LocalDateTime publishedOn;
    // file name
    private String screenshot;
    //path
    private String screenshotPath;
    private Folder folder;
}

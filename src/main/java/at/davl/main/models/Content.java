package at.davl.main.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "content")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contentId;

    @NotBlank(message = "The title field can't be blank")
    @Column(unique = true, name = "title")
    private String title;

    @NotBlank(message = "The content field can't be blank")
    @Column(name = "content")
    private String content;

    @Column(name = "publishedOn")
    private LocalDateTime publishedOn;

    // many screenshots
    @Column(nullable = true, name = "screenshots")
    private String screenshot;

    @ManyToOne
    @JoinColumn(name="folderId", nullable=false)
    private Folder folder;

    public Content(Integer contentId, String title, String content, String screenshot, Folder folder) {
        this.title = title;
        this.content = content;
        this.publishedOn = LocalDateTime.now();
        this.screenshot = screenshot;
        this.folder = folder;
    }
}
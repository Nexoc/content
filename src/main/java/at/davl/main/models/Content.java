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
    @Column(unique = false, name = "title", length = 500)
    private String title;

    @NotBlank(message = "The content field can't be blank")
    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

    @Column(name = "publishedOn")
    private LocalDateTime publishedOn;

    // many files?
    @Column(nullable = true, name = "file")
    private String file;

    // @ManyToOne
    // @JoinColumn(name="folderId", nullable=false)
    private Integer folderId;

    public Content(Integer contentId, String title, String content, String file, Integer folderId) {
        this.title = title;
        this.content = content;
        this.publishedOn = LocalDateTime.now();
        this.file = file;
        this.folderId = folderId;
    }
}
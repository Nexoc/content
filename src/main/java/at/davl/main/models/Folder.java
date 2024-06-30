package at.davl.main.models;

import at.davl.main.auth.entities.User;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer folderId;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Please provide title")
    private String title;

    @Column(name = "userId", nullable = false)
    //@ManyToOne
    //@JoinColumn(name="userId", nullable=false)
    private Integer userId;

    @ElementCollection
    @CollectionTable(name = "folder_contents", joinColumns = @JoinColumn(name = "folderId"))
    @Column(name = "contentId")
    // @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "folder")
    private Set<Integer> content;
}
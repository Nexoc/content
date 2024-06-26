package at.davl.main.dto;

import at.davl.main.models.Folder;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// backend to frontend
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDto {
    private Integer contentId;
    private String title;
    private String content;
    private LocalDateTime publishedOn;
    // file name
    private String file;
    //path
    private String filePath;
    private Integer folderId;
}
/*

(7 known properties: "filePath", "contentId", "title", "content", "file", "publishedOn", "folder"])
 at [Source: (String)"
 {
 "contentId":null,
 "title":"It Is my 1 title",
 "content":"ToolBar.vue -&gt; data() -&gt; initial_html and &lt;div&gt; line 134 ",
 "folderId":1}";

 line: 1, column: 140] (through reference chain: at.davl.main.dto.ContentDto["folderId"])

 */



/*
private Integer contentId;
private String title;
private String content;
private LocalDateTime publishedOn;
// file name
private String file;
//path
private String filePath;
private Integer folderId;

contentDto {
                         "contentId" : null,
                          "title" : "my title",
                         "content" : "my content",
                         "folderId" : 1,
                         "publishedOn" : 2024,
                         "file" : "any.png",
                         "filePath" : "IDK",
                         "folderId" : 1
                     }


                     contentDto {
                         "contentId" : null,
                          "title" : "my title",
                         "content" : "my content",
                         "folderId" : 1
                     }
 */
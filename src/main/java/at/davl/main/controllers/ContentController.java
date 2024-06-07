package at.davl.main.controllers;

import at.davl.main.dto.ContentDto;
import at.davl.main.dto.FolderDto;
import at.davl.main.exceptions.EmptyFileException;
import at.davl.main.service.ContentService;
import at.davl.main.service.FolderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/folders/{folderId}/contents")
public class ContentController {

    @Autowired
    private final ContentService contentService;

    public ContentController(ContentService contentService){
        this.contentService = contentService;
    }

    @PostMapping("/add-content")
    public ResponseEntity<ContentDto> addContentHandler(@RequestPart MultipartFile file,
                                                        @RequestPart String contentDto) throws IOException, EmptyFileException {
        if (file.isEmpty()) {
            throw new EmptyFileException("File is empty. Please send another file");
        }
        ContentDto convertedDto = convertToContentDto(contentDto);

        // todo send to .addContent as a variable folderId
        return new ResponseEntity<>(contentService.addContent(convertedDto, file), HttpStatus.CREATED);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ContentDto> getContentHandler(@PathVariable Integer userId,
                                                        @PathVariable Integer folderId,
                                                        @PathVariable Integer contentId){
        return ResponseEntity.ok(contentService.getContent(contentId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')") // @EnableMethodSecurity in SecurityConfiguration
    @GetMapping("/all")
    public ResponseEntity<List<ContentDto>> getAllContentsHandler() {
        return ResponseEntity.ok(contentService.getAllContent());
    }


    @PutMapping("/update/{contentId}")
    public ResponseEntity<ContentDto> updateContentHandler(@PathVariable Integer contentId,
                                                       @RequestPart MultipartFile file,
                                                       @RequestPart String contentDtoObj) throws IOException {

        if (file.isEmpty()) file = null;
        ContentDto convertedContent = convertToContentDto(contentDtoObj);
        return ResponseEntity.ok(contentService.updateContent(contentId, convertedContent, file));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/{contentId}")
    public ResponseEntity<String> deleteContentHandler(@PathVariable Integer contentId) throws IOException {
        return ResponseEntity.ok(contentService.deleteContent(contentId));
    }

    /*
    convert from String to contentDTO
    */
    private ContentDto convertToContentDto(String contentDtoObj) throws JsonProcessingException {
        // mapper (we need it for a map value to the contentDto class)
        ObjectMapper objectMapper = new ObjectMapper();
        // it is mapped content to contentDto
        ContentDto contentDto = objectMapper.readValue(contentDtoObj, ContentDto.class);
        return contentDto;
    }
}

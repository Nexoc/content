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

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/users/{userId}/folders")
public class FolderController {

    @Autowired
    private final FolderService folderService;
    @Autowired
    private final ContentService contentService;

    public FolderController(FolderService folderService, ContentService contentService) {
        this.folderService = folderService;
        this.contentService = contentService;
    }

    //@CrossOrigin(origins = "http://10.0.2.15:8080/")
    @CrossOrigin(origins = "#{corsConfig.allowedOrigin}")
    @PostMapping("/add-folder")
    public ResponseEntity<FolderDto> addFolderHandler(
            @RequestPart String folderDto) throws IOException, EmptyFileException {
        // convert from String to Dto
        FolderDto convertedDto = convertToFolderDto(folderDto);
        return new ResponseEntity<>(
                folderService.addFolder(convertedDto),
                HttpStatus.CREATED
        );
    }

    @CrossOrigin(origins = "#{corsConfig.allowedOrigin}")
    @GetMapping("/all")
    public ResponseEntity<List<FolderDto>> getAllFoldersByUserIdHandler(@PathVariable Integer userId) {
        return ResponseEntity.ok(folderService.getAllFoldersByUserId(userId));
    }

    @CrossOrigin(origins = "#{corsConfig.allowedOrigin}")
    // @PreAuthorize("hasAnyAuthority('ADMIN')") // @EnableMethodSecurity in SecurityConfiguration
    @GetMapping("/{folderId}")
    public ResponseEntity<FolderDto> getFolderHandler(@PathVariable Integer folderId){
        return ResponseEntity.ok(folderService.getFolder(folderId));
    }

    /*
    // @PreAuthorize("hasAnyAuthority('ADMIN')") // @EnableMethodSecurity in SecurityConfiguration
    @GetMapping("/all")
    public ResponseEntity<List<FolderDto>> getAllFoldersHandler() {
        return ResponseEntity.ok(folderService.getAllFolder());
    }
     */

    @CrossOrigin(origins = "#{corsConfig.allowedOrigin}")
    @PutMapping("/update/{folderId}")
    public ResponseEntity<FolderDto> updateFolderHandler(@PathVariable Integer userId,
                                                         @PathVariable Integer folderId,
                                                         @RequestPart String folderDtoObj) throws IOException {
        FolderDto folderDto = convertToFolderDto(folderDtoObj);
        return ResponseEntity.ok(folderService.updateFolder(folderId, folderDto));
    }

    @CrossOrigin(origins = "#{corsConfig.allowedOrigin}")
    // @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/{folderId}")
    public ResponseEntity<String> deleteFolderHandler(@PathVariable Integer userId,
                                                      @PathVariable Integer folderId) throws IOException {
        return ResponseEntity.ok(folderService.deleteFolder(folderId));
    }

    @CrossOrigin(origins = "#{corsConfig.allowedOrigin}")
    @GetMapping("/{folderId}/contents")
    public ResponseEntity<List<ContentDto>> getAllContentByFolderHandler(@PathVariable Integer userId,
                                                                         @PathVariable Integer folderId) {
        return ResponseEntity.ok(contentService.getAllContentByFolderId(folderId));
    }

    /*
    convert from String to folderDTO
    */
    private FolderDto convertToFolderDto(String folderDtoObj) throws JsonProcessingException {
        // mapper (we need it for a map value to the folderDto class)
        ObjectMapper objectMapper = new ObjectMapper();
        // it is mapped folder to FolderDto
        FolderDto folderDto = objectMapper.readValue(folderDtoObj, FolderDto.class);
        return folderDto;
    }
}

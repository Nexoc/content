package at.davl.movie.controllers;


import at.davl.movie.dto.FolderDto;
import at.davl.movie.dto.MovieDto;
import at.davl.movie.exceptions.EmptyFileException;
import at.davl.movie.service.FolderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/folder")
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')") // @EnableMethodSecurity in SecurityConfiguration
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


    @GetMapping("/{folderId}")
    public ResponseEntity<FolderDto> getFolderHandler(@PathVariable Integer folderId){
        return ResponseEntity.ok(folderService.getFolder(folderId));
    }


    @GetMapping("/all")
    public ResponseEntity<List<FolderDto>> getAllFoldersHandler() {
        return ResponseEntity.ok(folderService.getAllFolder());
    }


    @PutMapping("/update/{folderId}")
    public ResponseEntity<FolderDto> updateFolderHandler(@PathVariable Integer folderId,
                                                       @RequestPart String folderDtoObj) throws IOException {
        FolderDto folderDto = convertToFolderDto(folderDtoObj);
        return ResponseEntity.ok(folderService.updateFolder(folderId, folderDto));
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/{folderId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer folderId) throws IOException {
        return ResponseEntity.ok(folderService.deleteFolder(folderId));
    }


    /*
    convert from String to folderDTO
     */
    private FolderDto convertToFolderDto(String folderDtoObj) throws JsonProcessingException {
        // mapper (we need it for a map value to the MovieDto class)
        ObjectMapper objectMapper = new ObjectMapper();
        // it mapped movie to MovieDto
        FolderDto folderDto = objectMapper.readValue(folderDtoObj, FolderDto.class);
        return folderDto;
    }
}

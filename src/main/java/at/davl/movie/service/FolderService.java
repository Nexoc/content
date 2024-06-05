package at.davl.movie.service;


import at.davl.movie.dto.FolderDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FolderService {

    FolderDto addFolder(FolderDto movieDto, MultipartFile file) throws IOException;

    FolderDto getFolder(Integer folderId);

    List<FolderDto> getAllFolder();

    FolderDto updateFolder(Integer folderId, FolderDto folderDto, MultipartFile file) throws IOException;

    String deleteFolder(Integer folderId) throws IOException;

}

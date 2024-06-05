package at.davl.main.service;


import at.davl.main.dto.FolderDto;

import java.io.IOException;
import java.util.List;

public interface FolderService {

    FolderDto addFolder(FolderDto folderDto) throws IOException;

    FolderDto getFolder(Integer folderId);

    List<FolderDto> getAllFolder();

    FolderDto updateFolder(Integer folderId, FolderDto folderDto) throws IOException;

    String deleteFolder(Integer folderId) throws IOException;

}

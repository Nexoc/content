package at.davl.main.service;


import at.davl.main.auth.entities.User;
import at.davl.main.dto.FolderDto;

import java.io.IOException;
import java.util.List;

public interface FolderService {

    FolderDto addFolder(FolderDto folderDto) throws IOException;

    FolderDto getFolder(Integer folderId);

    List<FolderDto> getAllFolder();

    List<FolderDto> getAllFoldersByUserId (Integer userId);

    FolderDto updateFolder(Integer folderId, FolderDto folderDto) throws IOException;

    String deleteFolder(Integer folderId) throws IOException;

}

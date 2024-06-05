package at.davl.movie.service;

import at.davl.movie.dto.FolderDto;
import at.davl.movie.models.Folder;
import at.davl.movie.repository.FolderRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FolderServiceImpl implements FolderService{

    private final FolderRepository folderRepository;

    public FolderServiceImpl(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }


    @Override
    public FolderDto addFolder(FolderDto folderDto) throws IOException {

        Folder folder = new Folder(
                null, // it will be autogenerated automatically
                folderDto.getNumber(),
                folderDto.getUser(),
                folderDto.getContents()
        );

        // 2. save the folder object to DB
        Folder savedFolder = folderRepository.save(folder);

        // 3.
        FolderDto response = new FolderDto(
                savedFolder.getFolderId(),
                savedFolder.getNumber(),
                savedFolder.getUser(),
                savedFolder.getContents()
        );


        return response;
    }

    @Override
    public FolderDto getFolder(Integer folderId) {
        return null;
    }

    @Override
    public List<FolderDto> getAllFolder() {
        return List.of();
    }

    @Override
    public FolderDto updateFolder(Integer folderId, FolderDto folderDto) throws IOException {
        return null;
    }

    @Override
    public String deleteFolder(Integer folderId) throws IOException {
        return "";
    }
}

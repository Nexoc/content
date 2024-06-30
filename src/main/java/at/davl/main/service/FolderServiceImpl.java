package at.davl.main.service;

import at.davl.main.auth.entities.User;
import at.davl.main.auth.repositories.UserRepository;
import at.davl.main.dto.FolderDto;

import at.davl.main.exceptions.FolderNotFoundException;
import at.davl.main.exceptions.UserNotFoundException;
import at.davl.main.models.Content;
import at.davl.main.models.Folder;
import at.davl.main.repository.FolderRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FolderServiceImpl implements FolderService{

    @Autowired
    private final FolderRepository folderRepository;
    @Autowired
    private final UserRepository userRepository;

    public FolderServiceImpl(FolderRepository folderRepository, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
    }


    @Override
    public FolderDto addFolder(FolderDto folderDto) throws IOException {

        Folder folder = new Folder(
                null, // it will be autogenerated automatically
                folderDto.getTitle(),
                folderDto.getUserId(),
                folderDto.getContentIds()
        );


        // 2. save the folder object to DB
        Folder savedFolder = folderRepository.save(folder);

        // 3. add to response all data
        FolderDto response = new FolderDto(
                savedFolder.getFolderId(),
                savedFolder.getTitle(),
                savedFolder.getUserId(),
                savedFolder.getContent()
        );
        return response;
    }

    @Override
    public FolderDto getFolder(Integer folderId) {
        // 1. check the data in Db and if exists. fetch the data of given ID
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new FolderNotFoundException("Folder is not found with id = " + folderId));

        // 2. map to the FolderDto object and return it (Code repeating, bit it is to understand a concept
        System.out.println("FolderService Impl -> getFolder -> getContent " + folder.getContent() );
        FolderDto response = new FolderDto(
                folder.getFolderId(),
                folder.getTitle(),
                folder.getUserId(),
                folder.getContent()
        );
        return response;
    }

    @Override
    public List<FolderDto> getAllFolder() {
        // 1. fetch all data from DB
        List<Folder> folders = folderRepository.findAll();

        List<FolderDto> folderDtos = new ArrayList<>();

        // 2. iterate through the list, generate posterUrl for each poster objectFolderDto
        // and map to the FolderDto object
        for(Folder folder : folders) {
            FolderDto response = new FolderDto(
                    folder.getFolderId(),
                    folder.getTitle(),
                    folder.getUserId(),
                    folder.getContent()
            );
            // add this dto to the list
            folderDtos.add(response);
        }
        return folderDtos;
    }

    @Override
    public List<FolderDto> getAllFoldersByUserId(Integer userId) {
        List<Folder> folders = folderRepository.findAllFoldersByUserId(userId);
        List<FolderDto> folderDtos = new ArrayList<>();
        for (Folder folder : folders) {

            FolderDto response = new FolderDto(
                    folder.getFolderId(),
                    folder.getTitle(),
                    folder.getUserId(),
                    folder.getContent()
            );
            folderDtos.add(response);
        }
        return folderDtos;
    }


    @Override
    public FolderDto updateFolder(FolderDto folderDto) throws IOException {

        // 1. check if the folder obj exists with a given folderId
        Folder fold = folderRepository.findById(folderDto.getFolderId()).orElseThrow(()
                -> new FolderNotFoundException("Folder is not found with id = " + folderDto.getFolderId()));

        User user = userRepository.findById(folderDto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found" ));

        // 2. map it to Folder object
        Folder folder = new Folder(
                folderDto.getFolderId(),
                folderDto.getTitle(),
                folderDto.getUserId(),
                folderDto.getContentIds()
        );

        // 3. save the folder object -> return saved folder obj
        Folder updatedFolder = folderRepository.save(folder);

        // 5 map to FolderDto and return it
        FolderDto response = new FolderDto(
                folder.getFolderId(),
                folder.getTitle(),
                folder.getUserId(),
                folder.getContent()
        );
        return response;
    }


    @Override
    public String deleteFolder(Integer folderId) throws IOException {
        // 1. check if folder obj exists in DB
        Folder folder = folderRepository.findById(folderId).orElseThrow(()
                -> new FolderNotFoundException("Folder not found with id = " + folderId));
        // 2. delete the folder object
        folderRepository.delete(folder);
        return "Folder was deleted with id: " + folderId;
    }
}

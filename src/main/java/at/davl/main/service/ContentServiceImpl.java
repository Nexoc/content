package at.davl.main.service;

import at.davl.main.dto.ContentDto;

import at.davl.main.dto.FolderDto;
import at.davl.main.exceptions.*;
import at.davl.main.models.Content;
import at.davl.main.models.Folder;
import at.davl.main.repository.ContentRepository;
import at.davl.main.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ContentServiceImpl implements ContentService{

    private final ContentRepository contentRepository;
    private final FileService fileService;
    private final FolderRepository folderRepository;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public ContentServiceImpl(ContentRepository contentRepository, FileService fileService, FolderRepository folderRepository) {
        this.contentRepository = contentRepository;
        this.fileService = fileService;
        this.folderRepository = folderRepository;
    }


    @Override
    public ContentDto addContent(ContentDto contentDto, MultipartFile file) throws IOException {
        // 1. upload the file
        // 1.1 check if file already exists
        if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileExistsException("File already exists, please choose a different filename");
        }

        String uploadedFileName = fileService.uploadFile(path, file);

        // 2. set the value of field "poster" as filename
        contentDto.setScreenshot(uploadedFileName);

        Integer folderId = contentDto.getFolderId();
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new FolderNotFoundException("Content not found with id: " + folderId));;

        // 3. map dto to movie object
        Content content = new Content(
                null, // it will be autogenerated automatically
                contentDto.getTitle(),
                contentDto.getContent(),
                LocalDateTime.now(),
                contentDto.getScreenshot(),
                folder
                );


        // 4. save the movie object -> saved Movie object
        Content savedContent = contentRepository.save(content);

        // generate the poster URL (full path). for now all in one folder
        String posterUrl = baseUrl + "/file/" + uploadedFileName;

        // 6. map content object to DTO object and return it
        ContentDto response = new ContentDto(
                savedContent.getContentId(),
                savedContent.getTitle(),
                savedContent.getContent(),
                savedContent.getPublishedOn(),
                savedContent.getScreenshot(),
                posterUrl,
                savedContent.getFolder().getFolderId()
        );

        return response;
    }

    @Override
    public ContentDto getContent(Integer contentId) {

        // 1. check the data in Db and if exists. fetch the data of given ID
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new MovieNotFoundException("Screenshot is not found with id = " + contentId));

        // 2. generate screenshot URL
        String posterUrl = baseUrl + "/file/" + content.getScreenshot();

        // 3. map to the MovieDto object and return it (Code repeating, bit it is to understand a concept
        ContentDto response = new ContentDto(
                content.getContentId(),
                content.getTitle(),
                content.getContent(),
                content.getPublishedOn(),
                content.getScreenshot(),
                posterUrl,
                content.getFolder().getFolderId()
        );
        return response;
    }

    @Override
    public List<ContentDto> getAllContent() {
        // 1. fetch all data from DB
        List<Content> contents = contentRepository.findAll();

        List<ContentDto> contentDtos = new ArrayList<>();

        // 2. iterate through the list, generate posterUrl for each poster object
        // and map to the MovieDto object
        for(Content content : contents) {
            String posterUrl = baseUrl + "/file/" + content.getScreenshot();

            ContentDto response = new ContentDto(
                    content.getContentId(),
                    content.getTitle(),
                    content.getContent(),
                    content.getPublishedOn(),
                    content.getScreenshot(),
                    posterUrl,
                    content.getFolder().getFolderId()
            );
            // add this dto to the list
            contentDtos.add(response);
        }
        return contentDtos;
    }

    @Override
    public List<ContentDto> getAllContentByFolderId(Integer folderId) {

        List<Content> contents = contentRepository.findAllContentsByFolderId(folderId);

        List<ContentDto> contentDtos = new ArrayList<>();

        for (Content content : contents) {
            String posterUrl = baseUrl + "/file/" + content.getScreenshot();
            ContentDto response = new ContentDto(
                    content.getContentId(),
                    content.getTitle(),
                    content.getContent(),
                    content.getPublishedOn(),
                    content.getScreenshot(),
                    posterUrl,
                    content.getFolder().getFolderId()
            );
            contentDtos.add(response);
        }
        return contentDtos;
    }


    @Override
    public ContentDto updateContent(Integer contentId, ContentDto contentDto, MultipartFile file) throws IOException {
        // 1. check if the content obj exists with a given contentId
        Content cont = contentRepository.findById(contentId).orElseThrow(()
                -> new ContentNotFoundException("Movie not found with id = " + contentId));

        // 2. if file is null, do nothing
        // but if file is not null, then delete existing file associated with the record,
        // and upload the new file
        String fileName = cont.getScreenshot();
        if(file != null) {
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path, file);
        }

        // 3. set contentDto's screenshot value, according to step 2
        contentDto.setScreenshot(fileName);

        Integer folderId = contentDto.getFolderId();
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new FolderNotFoundException("Content not found with id: " + folderId));;

        // 4. map it to Content object
        Content content = new Content(
                cont.getContentId(),
                contentDto.getTitle(),
                contentDto.getContent(),
                contentDto.getPublishedOn(),
                contentDto.getScreenshot(),
                folder
        );

        // 5. save the movie object -> return saved movie obj
        Content updatedContent = contentRepository.save(content);

        // 6 screenshotUrl for it
        String screenshotUrl = baseUrl + "/file/" + fileName;

        // 7. map to ContentDto and return it
        ContentDto response = new ContentDto(
                content.getContentId(),
                content.getTitle(),
                content.getContent(),
                content.getPublishedOn(),
                content.getScreenshot(),
                screenshotUrl,
                content.getFolder().getFolderId()
        );
        return response;
    }

    @Override
    public String deleteContent(Integer contentId) throws IOException {

        // 1. check if content obj exists in DB
        Content cont = contentRepository.findById(contentId).orElseThrow(()
                -> new ContentNotFoundException("Movie not found with id = " + contentId));
        Integer id = cont.getContentId();
        // 2. delete the file associated with this object
        Files.deleteIfExists(Paths.get(path + File.separator + cont.getScreenshot()));
        // 3. delete the content object
        contentRepository.delete(cont);
        return "Content has been deleted with id: " + id;

    }
}

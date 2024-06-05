package at.davl.main.service;

import at.davl.main.dto.ContentDto;
import at.davl.main.models.Folder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ContentService {

    ContentDto addContent(ContentDto contentDto, MultipartFile file) throws IOException;

    ContentDto getContent(Integer contentId);

    List<ContentDto> getAllContent();

    List<ContentDto> getAllContentByFolder(Folder folder);

    ContentDto updateContent(Integer contentId, ContentDto contentDto, MultipartFile file) throws IOException;

    String deleteContent(Integer contentId) throws IOException;

}

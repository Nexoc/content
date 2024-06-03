package at.davl.movie.controllers;


import at.davl.movie.service.FileService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/")
public class FileController {

    private final FileService fileService;

    // read path from the application properties
    @Value("${project.poster}")
    private String path;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException {
        // get a name of the uploaded file
        String uploadedFileName = fileService.uploadFile(path, file);
        // return response and add to the json : File uploaded" + uploadedFileName
        return ResponseEntity.ok().body("File uploaded :" + uploadedFileName);
    }

    @GetMapping("/{fileName}")
    public void serverFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        // get a file in input stream
        InputStream resourceFile = fileService.getResourceFile(path, fileName);
        // it is converting to the *PNG format
        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        // convert to output stream and save it
        StreamUtils.copy(resourceFile, response.getOutputStream());
    }
}

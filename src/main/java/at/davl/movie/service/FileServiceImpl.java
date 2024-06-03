package at.davl.movie.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService{

    @Override
    // upload file
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // get name of the file
        String fileName = file.getOriginalFilename();

        // get the file path
        String filePath = path + File.separator + fileName;

        // create file Object
        File f = new File(path);
        // if path not exists -> create directory
        if (!f.exists()) {
            f.mkdirs();
        }

        // copy the file or upload file to the path and if the name of file will be the same
        // it will REPLACE_EXISTING file
        Files.copy(file.getInputStream(), Paths.get(filePath) /*, StandardCopyOption.REPLACE_EXISTING*/);

        return fileName;
    }

    @Override
    // download file
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {

        String filePath = path + File.separator + fileName;

        return new FileInputStream(filePath);


    }
}



























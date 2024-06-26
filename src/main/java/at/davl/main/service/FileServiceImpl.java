package at.davl.main.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileServiceImpl implements FileService{

    @Override
    // upload file
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // get name of the file
        String fileName = file.getOriginalFilename();

        // TODO
        String time = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
        // get the file path
        // it should be content / userId / folderId / fileName.png

        // String filePath = path + File.separator + userId + File.separator + folderId + File.separator + time + fileName;
        String filePath = path + File.separator + time + fileName;
        // create file Object
        // TODO for each user and each folder new folder
        File f = new File(path);
        // if path not exists -> create directories
        if (!f.exists()) {
            f.mkdirs();
        }
        /*
        File path3 = new File(path + File.separator + userId + File.separator + folderId);
        if (!path3.exists()) {
            path3.mkdirs();
        }
         */

        // copy the file or upload file to the path and if the name of file will be the same
        // it will REPLACE_EXISTING file
        Files.copy(file.getInputStream(), Paths.get(filePath) /*, StandardCopyOption.REPLACE_EXISTING*/);

        return time + fileName;
    }
    /*
    @PostMapping("/")
public String handleFileUpload(@RequestParam("manyfiles") MultipartFile[] files,
                           RedirectAttributes redirectAttributes) {

    for(MultipartFile file : files) {
        //Your upload code
    }
}
     */


    @Override
    // download file
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {

        String filePath = path + File.separator + fileName;

        return new FileInputStream(filePath);

    }
}



























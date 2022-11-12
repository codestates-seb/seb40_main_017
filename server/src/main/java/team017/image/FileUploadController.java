package team017.image;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileUploadController {
    private final S3Uploader s3Uploader;

//    private final S3Upload s3Upload;
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
//        return s3Uploader.upload(multipartFile, "main-project");
        return s3Uploader.upload(multipartFile);
    }

    @GetMapping("/test")
    public String index(){
        return "test";
    }
}

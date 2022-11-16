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
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3Uploader.upload(multipartFile); //data로 넘어오는 이미지 MultipartFile을 S#UploaderService로 전달
    }
}

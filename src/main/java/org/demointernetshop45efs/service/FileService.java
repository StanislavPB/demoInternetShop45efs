package org.demointernetshop45efs.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.demointernetshop45efs.entity.FileInfo;
import org.demointernetshop45efs.repository.FileInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileInfoRepository repository;
    private final AmazonS3 amazonS3;
    private final UserService userService;


    public String upload(MultipartFile file) throws IOException {

        String originalFileName = file.getOriginalFilename();
        // получаем исходное имя файла

        String extension = "";

        if (originalFileName != null){
            int indexExtension = originalFileName.lastIndexOf(".") + 1;
            // получаем индекс начала расширения полученного файла

            extension = originalFileName.substring(indexExtension);
        } else {
            throw new NullPointerException("Null original file name");
        }

        String uuid = UUID.randomUUID().toString(); // генерация случайного имени файла
        String newFileName = uuid + "." + extension;

        // загрузка файла в Digital Ocean

        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        // создаем запрос на отправку файла

        PutObjectRequest request = new PutObjectRequest(
                "demo-shop-files",
                "data/" + newFileName,
                inputStream,
                metadata
        ).withCannedAcl(CannedAccessControlList.PublicRead);

        amazonS3.putObject(request);

        // Сформируем ссылку на этот внешнее хранилище для нашего файла

        String link = amazonS3.getUrl("demo-shop-files","data/" + newFileName).toString();

        FileInfo fileInfo = new FileInfo();
        fileInfo.setLink(link);
        fileInfo.setUser(userService.getgetCurrentUser());

        repository.save(fileInfo);

        return "Файл " + link + " успешно сохранен";
    }


}

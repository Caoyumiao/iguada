package life.iGuaDa.community.service;

import life.iGuaDa.community.utils.FileUtils;
import life.iGuaDa.community.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
@Service
public class FileService {

    @Value("${img.location}")
    private String folder;

    public String upload(MultipartFile file) throws Exception{
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getOriginalFilename()
                .substring(file.getOriginalFilename().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8887/api/file/" + f.getName();
            return imgURL;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String upload(File file) {
        File imageFolder = new File(folder);
        File f = new File(imageFolder, StringUtils.getRandomString(6) + file.getName()
                .substring(file.getName().length() - 4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
            try {
                FileInputStream input = new FileInputStream(file.getPath());
                FileOutputStream out = new FileOutputStream(f.getPath());
                System.out.println(file.getPath());
                System.out.println(f.getPath());
                byte[] buffer = new byte[1024];
                int hasRead = 0;
                while ((hasRead = input.read(buffer)) > 0) {
                    out.write(buffer, 0, hasRead);
                }
                input.close();
                out.close();
                String imgUrl = "http://localhost:8887/api/file/" + f.getName();
                return imgUrl;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
    }

    public String upload(String url){
        File file = FileUtils.newFile(url);
        assert file != null;
        try {
            String imgUrl = upload(file);
            FileUtils.deleteFile(file);
            return imgUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}

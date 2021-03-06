package life.iGuaDa.community.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 处理图片的工具
 */
@Slf4j
public class FileUtils {
    private static List<String> suffix = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    static {
        suffix.add(".png");
        suffix.add(".bmp");
        suffix.add(".gif");
        suffix.add(".jpeg");
        suffix.add(".jpg");
    }

    public static boolean hasSuffix(String fileName) {
        for (String s : suffix) {
            if (StringUtils.endsWithIgnoreCase(fileName, s)) {
                return true;
            }
        }
        return false;
    }

    public static String newUUIDFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return fileName;
        }
        if (hasSuffix(fileName)) {
            String[] filePaths = fileName.split("\\.");
            if (filePaths.length > 1) {
                return UUID.randomUUID().toString().replace("-", "") + "." + filePaths[filePaths.length - 1];
            } else {
                return newUUIDPNGFileName();
            }
        } else {
            return newUUIDPNGFileName();
        }
    }

    public static String newUUIDPNGFileName() {
        return UUID.randomUUID().toString().replace("-", "") + ".png";
    }

    public static String newLocalFileName(String fileName) {
        String path = System.getProperty("user.dir") + File.separator;
        return newLocalFileName(path, fileName);
    }

    public static String newLocalFileName(String path, String fileName) {
        return path + newUUIDFileName(fileName);
    }

    public static File newFile(String url) {
        File file;
        String localFile = newLocalFileName(url);
        log.info("FILE_UTILS_NEW_INFO, url : {}, localFile : {}", url, localFile);
        file = new File(localFile);
        try {
            org.apache.commons.io.FileUtils.copyURLToFile(new URL(url), file);
        } catch (Exception e) {
            log.error("FILE_UTILS_NEW_ERROR, url : {}", url, e);
            return null;
        }
        return file;
    }

    public static void deleteFile(File file) {
        try {
            if(file.delete()) {
            	logger.info("File Delete Successful");
            }
        } catch (Exception e) {
            log.debug("FILE_UTILS_ERROR", e);
        }
    }

    public static void main(String[] args) {
        String x = FileUtils.newLocalFileName("https://s2.loli.net/2022/06/19/eSOv5Qafron3Pd7.png");
        logger.info(x);
    }
}

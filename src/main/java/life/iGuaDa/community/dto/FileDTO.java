package life.iGuaDa.community.dto;

import lombok.Data;

/**
 * 上传图片所用传参
 * Created by codedrinker on 2019/6/26.
 */
@Data
public class FileDTO {
    private int success;
    private String message;
    private String url;
}

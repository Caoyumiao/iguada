package life.iGuaDa.community.dto;

import lombok.Data;

/**
 * 创建评论所用传参
 * Created by codedrinker on 2019/5/30.
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}

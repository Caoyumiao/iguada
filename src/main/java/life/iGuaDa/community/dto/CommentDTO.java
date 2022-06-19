package life.iGuaDa.community.dto;

import life.iGuaDa.community.model.User;
import lombok.Data;

/**
 * 展示评论所用传参
 * Created by codedrinker on 2019/6/2.
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
}

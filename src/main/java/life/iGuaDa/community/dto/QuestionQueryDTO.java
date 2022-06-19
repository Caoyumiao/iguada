package life.iGuaDa.community.dto;

import lombok.Data;

/**
 * 实现查询所有传参
 * Created by codedrinker on 2019/7/1.
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private String sort;
    private Long time;
    private String tag;
    private Integer page;
    private Integer size;
}

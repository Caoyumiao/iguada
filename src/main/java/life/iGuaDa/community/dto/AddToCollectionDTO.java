package life.iGuaDa.community.dto;

import lombok.Data;

/**
 * 收藏所用传参
 */
@Data
public class AddToCollectionDTO {
    private Long userId;
    private Long QuestionId;
}

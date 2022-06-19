package life.iGuaDa.community.dto;

import lombok.Data;

@Data
public class QuestionQueryUserIdDTO {
    private Long userId;
    private int pageNo;
    private int num;
}

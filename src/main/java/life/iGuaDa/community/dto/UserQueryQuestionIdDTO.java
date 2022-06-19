package life.iGuaDa.community.dto;

import lombok.Data;

@Data
public class UserQueryQuestionIdDTO {
    private Long questionId;
    private int pageNo;
    private int num;
}

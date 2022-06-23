package life.iGuaDa.community.dto;

import life.iGuaDa.community.model.User;
import lombok.Data;

@Data
public class ReportDTO {
    private int id;
    private Long userId;
    private Long questionId;
    private String reason;
    private int deal;
    private User user;
}

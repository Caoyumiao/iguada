package life.iGuaDa.community.mapper;


import life.iGuaDa.community.dto.UserQueryQuestionIdDTO;
import life.iGuaDa.community.model.Question;

import java.util.List;

public interface UserExtMapper {

    List<Question> findUsersByQuestion(UserQueryQuestionIdDTO userQueryQuestionIdDTO);

    Long findUsersCountByQuestion(UserQueryQuestionIdDTO userQueryQuestionIdDTO);
}

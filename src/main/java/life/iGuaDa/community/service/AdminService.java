package life.iGuaDa.community.service;

import life.iGuaDa.community.exception.CustomizeErrorCode;
import life.iGuaDa.community.exception.CustomizeException;
import life.iGuaDa.community.mapper.QuestionMapper;
import life.iGuaDa.community.mapper.UserMapper;
import life.iGuaDa.community.model.Question;
import life.iGuaDa.community.model.QuestionExample;
import life.iGuaDa.community.model.User;
import life.iGuaDa.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public void blockUser(String accountId){
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(accountId);
        List<User> users = userMapper.selectByExample(userExample);
        if (!users.isEmpty()){
            User updateUser = users.get(0);
            updateUser.setDisable(1);
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(updateUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        } else {
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
        }
    }

    public void unblockUser(String accountId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(accountId);
        List<User> users = userMapper.selectByExample(userExample);
        if (!users.isEmpty()) {
            User updateUser = users.get(0);
            updateUser.setDisable(0);
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(updateUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        } else {
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
        }
    }

    public void blockQuestion(Long questionId) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andIdEqualTo(questionId);
        List<Question> questions = questionMapper.selectByExample(questionExample);
        if(!questions.isEmpty()) {
            Question updateQuestion = questions.get(0);
            updateQuestion.setDisable(1);
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(updateQuestion.getId());
            questionMapper.updateByExampleSelective(updateQuestion, example);
        } else {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
    }

    public void unblockQuestion(Long questionId) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andIdEqualTo(questionId);
        List<Question> questions = questionMapper.selectByExample(questionExample);
        if(!questions.isEmpty()) {
            Question updateQuestion = questions.get(0);
            updateQuestion.setDisable(0);
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(updateQuestion.getId());
            questionMapper.updateByExampleSelective(updateQuestion, example);
        } else {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
    }
}

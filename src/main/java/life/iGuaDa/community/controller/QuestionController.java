package life.iGuaDa.community.controller;

import life.iGuaDa.community.dto.CommentDTO;
import life.iGuaDa.community.dto.QuestionDTO;
import life.iGuaDa.community.enums.CommentTypeEnum;
import life.iGuaDa.community.exception.CustomizeErrorCode;
import life.iGuaDa.community.exception.CustomizeException;
import life.iGuaDa.community.service.CommentService;
import life.iGuaDa.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by codedrinker on 2019/5/21.
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") String id, Model model) {
        Long questionId = null;
        try {
            questionId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }
        QuestionDTO questionDTO = questionService.getById(questionId);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);
        Long collection = questionService.followQuestionNum(questionId);
        //累加阅读数
        questionService.incView(questionId);
        model.addAttribute("collectionnumber", collection);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}

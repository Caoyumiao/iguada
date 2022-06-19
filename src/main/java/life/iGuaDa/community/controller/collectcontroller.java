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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class collectcontroller {

    @Autowired
    private QuestionService questionService;


    @Autowired
    private CommentService commentService;

    @GetMapping ("/collect/{userid}/{questionid}")
    public String question(@PathVariable(name = "userid") Long id1, @PathVariable(name = "questionid") Long id2,  Model model) {

        System.out.println(id1);
        System.out.println(id2);
        QuestionDTO questionDTO = questionService.getById(id2);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id2, CommentTypeEnum.QUESTION);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        if(questionService.checkFromCollection(id1,id2)){

            Long collection = questionService.followQuestionNum(id2);
            model.addAttribute("collectionnumber", collection);
            return "question";
        }else{

            questionService.addToCollection(id1, id2);
            Long collection = questionService.followQuestionNum(id2);
            model.addAttribute("collectionnumber", collection);
            return "question";
        }
    }

    @GetMapping ("/collectdelete/{userid}/{questionid}")
    public String deletcollect(@PathVariable(name = "userid") Long id2, @PathVariable(name = "questionid") Long id1) {

        System.out.println(id1);
        System.out.println(id2);
        questionService.deleteFromCollection(id1, id2);
        return "redirect:/profile/collect";
    }



}

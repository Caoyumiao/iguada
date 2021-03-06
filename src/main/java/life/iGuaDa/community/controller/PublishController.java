package life.iGuaDa.community.controller;

import life.iGuaDa.community.cache.QuestionRateLimiter;
import life.iGuaDa.community.cache.TagCache;

import life.iGuaDa.community.dto.QuestionDTO;
import life.iGuaDa.community.model.Question;
import life.iGuaDa.community.model.User;
import life.iGuaDa.community.service.QuestionService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class PublishController {
    private static final String PUBLISH = "publish";
    private static final String ERROR = "error";
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRateLimiter questionRateLimiter;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("tags", TagCache.get());
        return PUBLISH;
    }


    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return PUBLISH;
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());

        if (StringUtils.isBlank(title)) {
            model.addAttribute(ERROR, "??????????????????");
            return PUBLISH;
        }

        if (StringUtils.length(title) > 50) {
            model.addAttribute(ERROR, "???????????? 50 ?????????");
            return PUBLISH;
        }
        if (StringUtils.isBlank(description)) {
            model.addAttribute(ERROR, "????????????????????????");
            return PUBLISH;
        }
        if (StringUtils.isBlank(tag)) {
            model.addAttribute(ERROR, "??????????????????");
            return PUBLISH;
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute(ERROR, "??????????????????:" + invalid);
            return PUBLISH;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute(ERROR, "???????????????");
            return PUBLISH;
        }

        if (user.getDisable() == 1) {
            model.addAttribute(ERROR, "????????????????????????????????????????????????");
            return PUBLISH;
        }

        if (questionRateLimiter.reachLimit(user.getId())) {
            model.addAttribute(ERROR, "??????????????????????????????");
            return PUBLISH;
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);

        int qid = questionService.createOrUpdate(question);

        return "redirect:/";
    }
}

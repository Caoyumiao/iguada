package life.iGuaDa.community.controller;

import life.iGuaDa.community.dto.ReportDTO;
import life.iGuaDa.community.dto.ResultDTO;
import life.iGuaDa.community.exception.CustomizeErrorCode;
import life.iGuaDa.community.model.Question;
import life.iGuaDa.community.model.Report;
import life.iGuaDa.community.model.User;
import life.iGuaDa.community.service.ReportService;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;

    @ResponseBody
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public Object post(@RequestBody ReportDTO reportDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        Question question = (Question) request.getSession().getAttribute("question");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (user.getDisable() == 1) {
            return ResultDTO.errorOf(CustomizeErrorCode.USER_DISABLE);
        }
        if (reportDTO == null || reportDTO.getQuestionId() == null ) {
            return ResultDTO.errorOf(CustomizeErrorCode.REPORT_QUESTION_NOT_FOUND);
        }
        if (StringUtils.isNullOrEmpty(reportDTO.getReason())){
            return ResultDTO.errorOf(CustomizeErrorCode.REASON_IS_EMPTY);
        }
        Report report  = new Report();
        report.setUserId(user.getId());
        report.setQuestionId(question.getId());
        report.setDeal(0);
        reportService.createOrUpdateReport(report);
        return ResultDTO.okOf();
    }
}

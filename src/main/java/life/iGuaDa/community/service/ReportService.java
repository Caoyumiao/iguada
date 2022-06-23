package life.iGuaDa.community.service;

import life.iGuaDa.community.dto.ReportDTO;
import life.iGuaDa.community.enums.NotificationStatusEnum;
import life.iGuaDa.community.enums.NotificationTypeEnum;
import life.iGuaDa.community.mapper.*;
import life.iGuaDa.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserMapper userMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ReportMapper reportMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private NotificationMapper notificationMapper;

    public void createNotify(Report report, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        Notification notification = new Notification();
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(report.getUserId());
        notification.setNotifierName("Admin");
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    public List<ReportDTO> listById(int id){
        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andIdEqualTo(id);
        List<Report> reports = reportMapper.selectByExample(reportExample);
        if(reports.size()==0){
            return new ArrayList<>();
        }
        Set<Long> reporters = reports.stream().map(report -> report.getUserId()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(reporters);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<ReportDTO> reportDTOS = reports.stream().map(report -> {
            ReportDTO reportDTO = new ReportDTO();
            BeanUtils.copyProperties(report,reportDTO);
            reportDTO.setUser(userMap.get(report.getUserId()));
            return reportDTO;
        }).collect(Collectors.toList());
        return reportDTOS;
    }

    public void createOrUpdateReport(Report report){
        ReportExample reportExample = new ReportExample();
        reportExample.createCriteria().andIdEqualTo(report.getId());
        List<Report> reports = reportMapper.selectByExample(reportExample);
        if (reports.isEmpty()) {
           reportMapper.insertSelective(report);
        } else {
            Report dbreport = reports.get(0);
            Report updateReport = new Report();
            updateReport.setId(report.getId());
            updateReport.setUserId(report.getUserId());
            updateReport.setQuestionId(report.getQuestionId());
            updateReport.setDeal(report.getDeal());
            ReportExample example = new ReportExample();
            example.createCriteria().andIdEqualTo(dbreport.getId());
            reportMapper.updateByExampleSelective(updateReport,example);
        }
    }

    public void changeDeal(Report report){
        QuestionExample questionExample = new QuestionExample();
        if(questionExample.createCriteria().andIdEqualTo(report.getQuestionId()) == null){
            report.setDeal(1);
        }else{
            report.setDeal(2);
        }
    }
}

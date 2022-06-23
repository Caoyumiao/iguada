package life.iGuaDa.community.schedule;

import life.iGuaDa.community.mapper.UserMapper;
import life.iGuaDa.community.model.User;
import life.iGuaDa.community.model.UserExample;
import life.iGuaDa.community.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class AvatarFixTasks {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void fixUserAvatar() {
        int offset = 0;
        int limit = 20;
        log.info("fixUserAvatar start {}", new Date());
        List<User> list = new ArrayList<>();
        while (offset == 0 || list.size() == limit) {
            UserExample userExample = new UserExample();
            userExample.setOrderByClause("id desc");
            list = userMapper.selectByExampleWithRowbounds(userExample, new RowBounds(offset, limit));
            for (User user : list) {
                try {
                    if (StringUtils.contains(user.getAvatarUrl(), "githubusercontent")) {
                            String fileUrl = fileService.upload(user.getAvatarUrl());
                            user.setAvatarUrl(fileUrl);
                            log.error("fixUserAvatar user : {}", user.getId());
                            userMapper.updateByPrimaryKey(user);
                        }
                    } catch (Exception e) {
                    log.error("fixUserAvatar error", e);
                }
            }
            offset += limit;
        }
        log.info("fixUserAvatar stop {}", new Date());
    }
}
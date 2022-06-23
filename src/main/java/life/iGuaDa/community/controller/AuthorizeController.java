package life.iGuaDa.community.controller;

import life.iGuaDa.community.model.User;
import life.iGuaDa.community.model.UserExample;
import life.iGuaDa.community.service.FileService;
import life.iGuaDa.community.mapper.UserMapper;
import life.iGuaDa.community.service.UserService;
import life.iGuaDa.community.strategy.LoginUserInfo;
import life.iGuaDa.community.strategy.UserStrategy;
import life.iGuaDa.community.strategy.UserStrategyFactory;
import life.iGuaDa.community.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
public class AuthorizeController {
    private static final String TOKEN = "token";
    private static final String REDIRECT = "redirect:/";
    @Autowired
    private UserStrategyFactory userStrategyFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @GetMapping("/callback/{type}")
    public String newCallback(@PathVariable(name = "type") String type,
                              @RequestParam(name = "code") String code,
                              @RequestParam(name = "state", required = false) String state,
                              HttpServletResponse response) {
        UserStrategy userStrategy = userStrategyFactory.getStrategy(type);
        LoginUserInfo loginUserInfo = userStrategy.getUser(code, state);
        if (loginUserInfo != null && loginUserInfo.getId() != null) {
            User user = new User();
            String accountId = StringUtils.getRandomString(16);
            Boolean isOccupied = true;
            while(isOccupied){
                UserExample userExample = new UserExample();
                userExample.createCriteria().andAccountIdEqualTo(accountId);
                List<User> users = userMapper.selectByExample(userExample);
                if(users != null && users.size() != 0){
                    isOccupied = true;
                }
                else isOccupied = false;
            }
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(loginUserInfo.getName());
            user.setAccountId(accountId);
            user.setType(type);
            user.setIdentity(0);
            user.setDisable(0);
            try {
                String fileUrl = fileService.upload(loginUserInfo.getAvatarUrl());
                user.setAvatarUrl(fileUrl);
            } catch (Exception e) {
                user.setAvatarUrl(loginUserInfo.getAvatarUrl());
            }
            userService.createOrUpdate(user);
            Cookie cookie = new Cookie(TOKEN, token);
            cookie.setMaxAge(60 * 60 * 24 * 30 * 6);
            cookie.setPath("/");
            response.addCookie(cookie);
            return REDIRECT;
        } else {
            log.error("callback get github error,{}", loginUserInfo);
            // 登录失败，重新登录
            return REDIRECT;
        }
    }
    /**
     * 实现登出功能
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().invalidate();
        Cookie cookie = new Cookie(TOKEN, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return REDIRECT;
    }
}

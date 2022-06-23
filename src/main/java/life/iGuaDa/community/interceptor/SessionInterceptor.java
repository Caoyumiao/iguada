package life.iGuaDa.community.interceptor;

import life.iGuaDa.community.mapper.UserMapper;
import life.iGuaDa.community.model.User;
import life.iGuaDa.community.model.UserExample;
import life.iGuaDa.community.service.NotificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;
    @Value("${github.redirect.uri}")
    private String githubRedirectUri;

    @Value("${gitee.redirect.uri}")
    private String giteeRedirectUri;
    /**
     * @description:  每次请求前执行
     *                如果用户已登录， 从用户cookie中取得user个人信息与未读消息并加入session中
     *
     *
     * @param: request
     * @param: response
     * @param: handler
     * @return: boolean true请求继续执行， false请求中断
     * @author LiPeishu
     * @date: 2021/12/11 10:42
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置 context 级别的属性
        request.getServletContext().setAttribute("giteeRedirectUri", giteeRedirectUri);
        request.getServletContext().setAttribute("githubRedirectUri", githubRedirectUri);
        // 没有登录的时候也可以查看导航
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token") && StringUtils.isNotBlank(cookie.getValue())) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (!users.isEmpty()) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", users.get(0));
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        session.setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        // Do nothing because of .
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // Do nothing because of .
    }
}

package life.iGuaDa.community.controller;
import life.iGuaDa.community.mapper.UserMapper;
import life.iGuaDa.community.model.UserExample;
import life.iGuaDa.community.model.User;
import life.iGuaDa.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/login")
    public String LoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String Login(HttpServletRequest request, HttpServletResponse response, Model model) {
        String accountId = request.getParameter("accountId");
        String password = request.getParameter("password");
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(accountId)
                .andPasswordEqualTo(password);

        List<User> users = userMapper.selectByExample(userExample);
        if (users != null && users.size() != 0) {
            User user = users.get(0);
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            userMapper.updateByPrimaryKey(user);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            model.addAttribute("loginFail", "fail");
            return "login";
        }

    }

}

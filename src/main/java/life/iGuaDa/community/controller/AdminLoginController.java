package life.iGuaDa.community.controller;

import life.iGuaDa.community.mapper.UserMapper;
import life.iGuaDa.community.model.User;
import life.iGuaDa.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public class AdminLoginController {
    @Autowired
    private UserMapper userMapper;

    private static final String ERROR = "error";

    @GetMapping("/adminLogin")
    public String adminLoginPage() {
        return "adminLogin";
    }

    @PostMapping("/adminLogin")
    public String adminLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
        String accountId = request.getParameter("accountId");
        String password = request.getParameter("password");
        String rememberFlag = request.getParameter("rememberFlag");
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(accountId)
                .andPasswordEqualTo(password);

        List<User> users = userMapper.selectByExample(userExample);
        if (users != null && users.size() != 0) {
            User user = users.get(0);
            if(user.getIdentity() != 1){
                model.addAttribute(ERROR, "权限不足");
                return "adminLogin";
            } else {
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                userMapper.updateByPrimaryKey(user);
                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                if (rememberFlag != null) {
                    cookie.setMaxAge(60 * 60 * 24);
                }
                response.addCookie(cookie);
                return "redirect:/";
            }
        } else {
            model.addAttribute("adminLoginFail", "fail");
            return "adminLogin";
        }

    }

}

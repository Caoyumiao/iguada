package life.iGuaDa.community.controller;

import life.iGuaDa.community.exception.CustomizeErrorCode;
import life.iGuaDa.community.model.User;
import life.iGuaDa.community.dto.ResultDTO;
import life.iGuaDa.community.mapper.UserMapper;
import life.iGuaDa.community.model.UserExample;
import life.iGuaDa.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RegisterController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerPage() {

        return "register";
    }

    private static final String ERROR = "error";

    @PostMapping("/register")
    public ResultDTO register(HttpServletRequest request, Model model) {
        String accountId = request.getParameter("accountId");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId);
        List<User> users = userMapper.selectByExample(userExample);
        if (users != null && users.size() != 0) {
            model.addAttribute(ERROR,"ID已被占用");
            return ResultDTO.errorOf(CustomizeErrorCode.ID_OCCUPIED);
        } else {
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            user.setAccountId(accountId);
            user.setDisable(0);
            user.setIdentity(0);
            user.setType("iGuaDa");
            user.setAvatarUrl("https://sm.ms/image/xFCiQkL4sVoZY13");
            userService.createOrUpdate(user);
            model.addAttribute("signupSuccess", "success");
            return ResultDTO.okOf();
        }
    }

}

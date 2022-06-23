package life.iGuaDa.community.controller;

import life.iGuaDa.community.dto.ResultDTO;
import life.iGuaDa.community.dto.UserDTO;
import life.iGuaDa.community.exception.CustomizeErrorCode;
import life.iGuaDa.community.mapper.UserMapper;
import life.iGuaDa.community.model.User;
import life.iGuaDa.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
    public Object post(@RequestBody UserDTO userDTO, String rememberFlag) {
        String accountId = userDTO.getAccountId();
        String password = userDTO.getPassword();
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(accountId)
                .andPasswordEqualTo(password);

        List<User> users = userMapper.selectByExample(userExample);
        if (users != null && users.size() != 0) {
            User user = users.get(0);
            if(user.getIdentity() != 1){
                return ResultDTO.errorOf(CustomizeErrorCode.PERMISSION_DENIED);
            } else {
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                userMapper.updateByPrimaryKey(user);
                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                if (rememberFlag != null) {
                    cookie.setMaxAge(60 * 60 * 24);
                }
                return ResultDTO.okOf();
            }
        } else {
            return ResultDTO.errorOf(CustomizeErrorCode.LOGIN_FAIL);
        }

    }

}

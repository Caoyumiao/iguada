package life.iGuaDa.community.controller;

import life.iGuaDa.community.dto.CommentCreateDTO;
import life.iGuaDa.community.dto.UserDTO;
import life.iGuaDa.community.exception.CustomizeErrorCode;
import life.iGuaDa.community.model.User;
import life.iGuaDa.community.dto.ResultDTO;
import life.iGuaDa.community.mapper.UserMapper;
import life.iGuaDa.community.model.UserExample;
import life.iGuaDa.community.service.FileService;
import life.iGuaDa.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
public class RegisterController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;


    @Autowired
    private FileService fileService;
    @GetMapping("/register")
    public String registerPage() {

        return "register";
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object post(@RequestBody UserDTO userDTO,
                       HttpServletRequest request) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(userDTO.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users != null && users.size() != 0) {
            return ResultDTO.errorOf(CustomizeErrorCode.ID_OCCUPIED);
        } else {
            User user = new User();
            user.setName(userDTO.getName());
            user.setPassword(userDTO.getPassword());
            user.setAccountId(userDTO.getAccountId());
            user.setToken(UUID.randomUUID().toString());
            user.setDisable(0);
            user.setIdentity(0);
            user.setType("iGuaDa");
            user.setAvatarUrl("https://s2.loli.net/2022/06/20/xFCiQkL4sVoZY13.webp");
            userService.createOrUpdate(user);
            return ResultDTO.okOf();
        }
    }

}

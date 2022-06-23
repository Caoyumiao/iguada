package life.iGuaDa.community.controller;

import life.iGuaDa.community.dto.QuestionDTO;
import life.iGuaDa.community.dto.ResultDTO;
import life.iGuaDa.community.dto.UserDTO;
import life.iGuaDa.community.exception.CustomizeErrorCode;
import life.iGuaDa.community.service.AdminService;
import life.iGuaDa.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@Controller
public class AdminController {

    private static final String ERROR = "error";

    @Autowired
    private AdminService adminService;

    @ResponseBody
    @RequestMapping(value = "/blockQuestion", method = RequestMethod.POST)
    public Object blockQuestion(@RequestBody QuestionDTO questionDTO, HttpServletRequest request) {
       long  test=questionDTO.getId();

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (user.getIdentity() != 1) {
            return ResultDTO.errorOf(CustomizeErrorCode.PERMISSION_DENIED);
        }

        adminService.blockQuestion(questionDTO.getId());
        return ResultDTO.okOf();
    }


    @ResponseBody
    @RequestMapping(value = "/blockUser", method = RequestMethod.POST)
    public Object blockUser(@RequestBody UserDTO userDTO, HttpServletRequest request){
        System.out.println(userDTO.getAccountId());
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (user.getIdentity() != 1) {
            return ResultDTO.errorOf(CustomizeErrorCode.PERMISSION_DENIED);
        }
        adminService.blockUser(userDTO.getAccountId());
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/unblockUser", method = RequestMethod.POST)
    public Object unblockUser(@RequestBody UserDTO userDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (user.getIdentity() != 1) {
            return ResultDTO.errorOf(CustomizeErrorCode.PERMISSION_DENIED);
        }
        adminService.unblockUser(userDTO.getAccountId());
        return ResultDTO.okOf();
    }
}

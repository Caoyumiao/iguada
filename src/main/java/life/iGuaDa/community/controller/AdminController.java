package life.iGuaDa.community.controller;

import life.iGuaDa.community.dto.ResultDTO;
import life.iGuaDa.community.exception.CustomizeErrorCode;
import life.iGuaDa.community.service.AdminService;
import life.iGuaDa.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

public class AdminController {

    private static final String ERROR = "error";

    @Autowired
    private AdminService adminService;

    @ResponseBody
    @RequestMapping(value = "/blockQuestion", method = RequestMethod.POST)
    public Object blockQuestion(@RequestBody Long id, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (user.getIdentity() != 1) {
            return ResultDTO.errorOf(CustomizeErrorCode.PERMISSION_DENIED);
        }

        adminService.blockQuestion(id);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/unblockQuestion", method = RequestMethod.POST)
    public Object unblockQuestion(@RequestBody Long id, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (user.getIdentity() != 1) {
            return ResultDTO.errorOf(CustomizeErrorCode.PERMISSION_DENIED);
        }

        adminService.unblockQuestion(id);
        return ResultDTO.okOf();
    }


    @ResponseBody
    @RequestMapping(value = "/blockUser", method = RequestMethod.POST)
    public Object blockUser(@RequestBody String accountId, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (user.getIdentity() != 1) {
            return ResultDTO.errorOf(CustomizeErrorCode.PERMISSION_DENIED);
        }
        adminService.blockUser(accountId);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/unblockUser", method = RequestMethod.POST)
    public Object unblockUser(@RequestBody String accountId, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (user.getIdentity() != 1) {
            return ResultDTO.errorOf(CustomizeErrorCode.PERMISSION_DENIED);
        }
        adminService.unblockUser(accountId);
        return ResultDTO.okOf();
    }
}

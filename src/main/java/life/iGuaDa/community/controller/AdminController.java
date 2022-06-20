package life.iGuaDa.community.controller;

import life.iGuaDa.community.dto.ResultDTO;
import life.iGuaDa.community.exception.CustomizeErrorCode;
import life.iGuaDa.community.service.AdminService;
import life.iGuaDa.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

public class AdminController {

    private static final String ERROR = "error";

    @Autowired
    private AdminService adminService;

    @GetMapping("/blockQuestion/{id}")
    public ResultDTO doBlockQuestion(@PathVariable(name = "id")Long id, HttpServletRequest request, Model model){

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute(ERROR, "管理员未登录");
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (user.getIdentity() == 0) {
            model.addAttribute(ERROR, "权限不足");
            return ResultDTO.errorOf(CustomizeErrorCode.PERMISSION_DENIED);
        }

        adminService.blockQuestion(id);
        model.addAttribute("BlockSuccess", "success");
        return ResultDTO.okOf();
    }

    public ResultDTO doUnblockQuestion(@PathVariable(name = "id")Long id, HttpServletRequest request, Model model){

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute(ERROR, "管理员未登录");
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (user.getIdentity() == 0) {
            model.addAttribute(ERROR, "权限不足");
            return ResultDTO.errorOf(CustomizeErrorCode.PERMISSION_DENIED);
        }

        adminService.unblockQuestion(id);
        model.addAttribute("UnblockSuccess", "success");
        return ResultDTO.okOf();
    }

    public ResultDTO doBlockUser(@PathVariable(name = "accountId")String accountId, HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute(ERROR, "管理员未登录");
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (user.getIdentity() == 0) {
            model.addAttribute(ERROR, "权限不足");
            return ResultDTO.errorOf(CustomizeErrorCode.PERMISSION_DENIED);
        }
        adminService.blockUser(accountId);
        model.addAttribute("BlockSuccess", "success");
        return ResultDTO.okOf();
    }

    public ResultDTO doUnblockUser(@PathVariable(name = "accountId")String accountId, HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute(ERROR, "管理员未登录");
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (user.getIdentity() == 0) {
            model.addAttribute(ERROR, "权限不足");
            return ResultDTO.errorOf(CustomizeErrorCode.PERMISSION_DENIED);
        }
        adminService.blockUser(accountId);
        model.addAttribute("UnblockSuccess", "success");
        return ResultDTO.okOf();
    }
}

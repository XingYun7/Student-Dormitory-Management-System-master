package com.sdms.controller;

import static com.sdms.common.result.LayuiResult.ResultCode.*;
import static com.sdms.common.util.StringUtils.parseLongList;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.LayuiResult;
import com.sdms.entity.User;
import com.sdms.service.RoleService;
import com.sdms.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Api("用户相关api")
@Controller
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @ApiOperation("登录认证")
    @PostMapping("/login-auth")
    public String login(String username, String password, RedirectAttributes attributes) {
        val result = userService.signIn(username, password);
        if (result.isSuccess()) {
            val roleId = result.getValue().getRole().getId();
            switch (roleId.intValue()) {
                case 1: // 学生角色的id是1
                    return "redirect:/index";
                case 2:  // 管理员角色的id是2
                    return "redirect:/admin/index";
                default:
                    attributes.addFlashAttribute("username", username);
                    attributes.addFlashAttribute("error", "未知角色");
                    return "redirect:/login";
            }
        } else {
            attributes.addFlashAttribute("username", username);
            attributes.addFlashAttribute("error", result.getMsg());
            return "redirect:/login";
        }
    }

    @ApiOperation("安全退出")
    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public String logout(RedirectAttributes attributes) {
        val result = userService.signOut();
        if (result.isSuccess()) {
            val username = result.getValue().getUsername();
            attributes.addFlashAttribute("info", (username == null ? "未知用户" : username) + "已安全退出");
        }
        return "redirect:/login";
    }

    @GetMapping(value = {"/admin/user-list"})
    public String toAdminUserList(Model model) {
        model.addAttribute("roles", roleService.listAllRoles());
        return "admin/user-list"; // Thymeleaf模板的名字,表示 templates/admin/user-list.html
    }

    @ApiOperation("ajax:分页查询用户信息")
    @RequestMapping(value = "/admin/users", method = {RequestMethod.POST})
    @ResponseBody
    public Page<User> fetchPage(@RequestBody PageRequest pageRequest) {
        return userService.fetchPage(pageRequest);
    }

    @ApiOperation("ajax:根据id查询用户")
    @GetMapping("/admin/user/{id}")
    @ResponseBody
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @ApiOperation("跳转到用户编辑界面")
    @GetMapping("/admin/user/edit")
    public String toEditUserById(@RequestParam(defaultValue = "-1") long id, Model model) {
        val user = userService.getUserById(id);
        if (user != null) {
            model.addAttribute("operation", "编辑用户");
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.listAllRoles());
            return "admin/user-input";// Thymeleaf模板的名字,表示 templates/admin/user-input.html
        } else {
            return "redirect:/admin/user-list";
        }
    }

    @ApiOperation("跳转到用户添加界面")
    @GetMapping("/admin/user/create")
    public String toCreateUser(Model model) {
        model.addAttribute("operation", "添加用户");
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.listAllRoles());
        return "admin/user-input";
    }

    @ApiOperation("保存用户")
    @GetMapping("/admin/user/save")
    public String saveUser(User user, RedirectAttributes attributes) {
        val result = userService.saveUser(user);
        if (result.isSuccess()) {
            attributes.addFlashAttribute("info", "操作成功");
        } else {
            attributes.addFlashAttribute("error", result.getMsg() + ",保存用户失败");
        }
        return "redirect:/admin/user-list";
    }

    @ApiOperation("ajax:根据若干id删除用户")
    @RequestMapping(value = "/admin/user/delete", method = {RequestMethod.POST})
    @ResponseBody
    public LayuiResult<String> deleteUserByIds(String ids) {
        val idList = parseLongList(ids);
        if (userService.deleteUserByIds(idList).isSuccess()) {
            return new LayuiResult<>(SUCCESS, null, null);
        } else {
            return new LayuiResult<>(FAILED, null, null);
        }
    }

    @ApiOperation("修改个人信息")
    @GetMapping("/user/update-info")
    public String updateUserInfo(User user, RedirectAttributes attributes) {
        val result = userService.updateUser(user);
        if (result.isSuccess()) {
            attributes.addFlashAttribute("info", "操作成功");
        } else {
            attributes.addFlashAttribute("error", result.getMsg() + ",更新信息失败");
        }
        return "redirect:/login";
    }
}

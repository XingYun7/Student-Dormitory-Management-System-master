package com.sdms.controller;

import com.sdms.service.CategoryService;
import com.sdms.service.RoomService;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class IndexController {

    @Resource
    private RoomService roomService;

    @Resource
    private CategoryService categoryService;

    @GetMapping(value = {"/login"})
    public String toLogin() {
        return "login"; // Thymeleaf模板的名字,表示 templates/login.html
    }

    @GetMapping(value = {"/", "/index"})
    public String toIndex(Model model) {
        val room = roomService.getCurrentStudentRoom();
        model.addAttribute("currentRoom", room);
        model.addAttribute("categories", categoryService.listAllCategories());
        return "index"; // Thymeleaf模板的名字,表示 templates/index.html
    }

    @GetMapping(value = {"/admin/index"})
    public String toAdminIndex() {
        return "admin/index"; // Thymeleaf模板的名字,表示 templates/admin/index.html
    }

    @GetMapping(value = {"/user"})
    public String toUser() {
        return "user"; // Thymeleaf模板的名字,表示 templates/user.html
    }
}

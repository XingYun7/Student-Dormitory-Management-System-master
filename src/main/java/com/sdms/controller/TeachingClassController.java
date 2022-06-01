package com.sdms.controller;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.LayuiResult;
import com.sdms.entity.TeachingClass;
import com.sdms.service.TeachingClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

import static com.sdms.common.result.LayuiResult.ResultCode.FAILED;
import static com.sdms.common.result.LayuiResult.ResultCode.SUCCESS;
import static com.sdms.common.util.StringUtils.parseLongList;

@Api("教学班级相关api")
@Controller
public class TeachingClassController {

    @Resource
    private TeachingClassService teachingClassService;

    @GetMapping(value = {"/admin/teaching-class-list"})
    public String toAdminTeachingClassList() {
        return "admin/teaching-class-list"; // Thymeleaf模板的名字,表示 templates/admin/teaching-class-list.html
    }

    @ApiOperation("ajax:分页查询班级信息")
    @RequestMapping(value = "/admin/teaching-classes", method = {RequestMethod.POST})
    @ResponseBody
    public Page<TeachingClass> fetchPage(@RequestBody PageRequest pageRequest) {
        return teachingClassService.fetchPage(pageRequest);
    }

    @ApiOperation("ajax:根据id查询班级")
    @GetMapping("/admin/teaching-class/{id}")
    @ResponseBody
    public TeachingClass getTeachingClassById(@PathVariable Long id) {
        return teachingClassService.getTeachingClassById(id);
    }

    @ApiOperation("跳转到班级编辑界面")
    @GetMapping("/admin/teaching-class/edit")
    public String toEditTeachingClassById(@RequestParam(defaultValue = "-1") long id, Model model) {
        val teachingClass = teachingClassService.getTeachingClassById(id);
        if (teachingClass != null) {
            model.addAttribute("operation", "编辑班级");
            model.addAttribute("teachingClass", teachingClass);
            return "admin/teaching-class-input";// Thymeleaf模板的名字,表示 templates/admin/teaching-class-input.html
        } else {
            return "redirect:/admin/teaching-class-list";
        }
    }

    @ApiOperation("跳转到班级添加界面")
    @GetMapping("/admin/teaching-class/create")
    public String toCreateTeachingClass(Model model) {
        model.addAttribute("operation", "添加班级");
        model.addAttribute("teachingClass", new TeachingClass());
        return "admin/teaching-class-input";
    }

    @ApiOperation("保存班级")
    @GetMapping("/admin/teaching-class/save")
    public String saveTeachingClass(TeachingClass teachingClass, RedirectAttributes attributes) {
        val result = teachingClassService.saveTeachingClass(teachingClass);
        if (result.isSuccess()) {
            attributes.addFlashAttribute("info", "操作成功");
        } else {
            attributes.addFlashAttribute("error", result.getMsg() + ",保存班级失败");
        }
        return "redirect:/admin/teaching-class-list";
    }

    @ApiOperation("ajax:根据若干id删除班级")
    @RequestMapping(value = "/admin/teaching-class/delete", method = {RequestMethod.POST})
    @ResponseBody
    public LayuiResult<String> deleteTeachingClassByIds(String ids) {
        val idList = parseLongList(ids);
        if (teachingClassService.deleteTeachingClassByIds(idList).isSuccess()) {
            return new LayuiResult<>(SUCCESS, null, null);
        } else {
            return new LayuiResult<>(FAILED, null, null);
        }
    }
}

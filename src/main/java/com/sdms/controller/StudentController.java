package com.sdms.controller;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.LayuiResult;
import com.sdms.entity.Student;
import com.sdms.service.TeachingClassService;
import com.sdms.service.StudentService;
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
import static com.sdms.common.util.StringUtils.parseStringList;

@Api("学生相关api")
@Controller
public class StudentController {

    @Resource
    private StudentService studentService;

    @Resource
    private TeachingClassService teachingClassService;

    @GetMapping(value = {"/admin/student-list"})
    public String toAdminStudentList(Model model) {
        model.addAttribute("teachingClasses", teachingClassService.listAllTeachingClasses());
        return "admin/student-list"; // Thymeleaf模板的名字,表示 templates/admin/student-list.html
    }

    @ApiOperation("ajax:分页查询学生信息")
    @RequestMapping(value = "/admin/students", method = {RequestMethod.POST})
    @ResponseBody
    public Page<Student> fetchPage(@RequestBody PageRequest pageRequest) {
        return studentService.fetchPage(pageRequest);
    }

    @ApiOperation("ajax:根据id查询学生")
    @GetMapping("/admin/student")
    @ResponseBody
    public Student getStudentById(String id) {
        return studentService.getStudentById(id);
    }

    @ApiOperation("跳转到学生详情界面")
    @GetMapping("/admin/student/detail")
    public String toDisplayStudentDetailById(@RequestParam(defaultValue = "") String id, Model model) {
        val student = studentService.getStudentById(id);
        if (student != null) {
            model.addAttribute("student", student);
            return "admin/student-detail";// Thymeleaf模板的名字,表示 templates/admin/student-detail.html
        } else {
            return "redirect:/admin/student-list";
        }
    }

    @ApiOperation("跳转到学生编辑界面")
    @GetMapping("/admin/student/edit")
    public String toEditStudentById(@RequestParam(defaultValue = "") String id, Model model) {
        val student = studentService.getStudentById(id);
        if (student != null) {
            model.addAttribute("operation", "编辑学生");
            model.addAttribute("student", student);
            model.addAttribute("teachingClasses", teachingClassService.listAllTeachingClasses());
            return "admin/student-input";// Thymeleaf模板的名字,表示 templates/admin/student-input.html
        } else {
            return "redirect:/admin/student-list";
        }
    }

    @ApiOperation("跳转到学生添加界面")
    @GetMapping("/admin/student/create")
    public String toCreateStudent(Model model) {
        model.addAttribute("operation", "添加学生");
        model.addAttribute("student", new Student());
        model.addAttribute("teachingClasses", teachingClassService.listAllTeachingClasses());
        return "admin/student-input";
    }

    @ApiOperation("保存学生")
    @GetMapping("/admin/student/save")
    public String saveStudent(Student student, RedirectAttributes attributes) {
        val result = studentService.saveStudent(student);
        if (result.isSuccess()) {
            attributes.addFlashAttribute("info", "操作成功");
        } else {
            attributes.addFlashAttribute("error", result.getMsg() + ",保存学生失败");
        }
        return "redirect:/admin/student-list";
    }

    @ApiOperation("ajax:根据若干id删除学生")
    @RequestMapping(value = "/admin/student/delete", method = {RequestMethod.POST})
    @ResponseBody
    public LayuiResult<String> deleteStudentByIds(String ids) {
        val idList = parseStringList(ids);
        if (studentService.deleteStudentByIds(idList).isSuccess()) {
            return new LayuiResult<>(SUCCESS, null, null);
        } else {
            return new LayuiResult<>(FAILED, null, null);
        }
    }
}

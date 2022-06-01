package com.sdms.controller;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.LayuiResult;
import com.sdms.entity.Category;
import com.sdms.service.CategoryService;
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

@Api("寝室类型相关api")
@Controller
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping(value = {"/admin/category-list"})
    public String toAdminCategoryList() {
        return "admin/category-list"; // Thymeleaf模板的名字,表示 templates/admin/category-list.html
    }

    @ApiOperation("ajax:分页查询寝室类型信息")
    @RequestMapping(value = "/admin/categories", method = {RequestMethod.POST})
    @ResponseBody
    public Page<Category> fetchPage(@RequestBody PageRequest pageRequest) {
        return categoryService.fetchPage(pageRequest);
    }

    @ApiOperation("ajax:根据id查询寝室类型")
    @GetMapping("/admin/category/{id}")
    @ResponseBody
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @ApiOperation("跳转到寝室类型编辑界面")
    @GetMapping("/admin/category/edit")
    public String toEditCategoryById(@RequestParam(defaultValue = "-1") long id, Model model) {
        val category = categoryService.getCategoryById(id);
        if (category != null) {
            model.addAttribute("operation", "编辑寝室类型");
            model.addAttribute("category", category);
            return "admin/category-input";// Thymeleaf模板的名字,表示 templates/admin/category-input.html
        } else {
            return "redirect:/admin/category-list";
        }
    }

    @ApiOperation("跳转到寝室类型添加界面")
    @GetMapping("/admin/category/create")
    public String toCreateCategory(Model model) {
        model.addAttribute("operation", "添加寝室类型");
        model.addAttribute("category", new Category());
        return "admin/category-input";
    }

    @ApiOperation("保存寝室类型")
    @GetMapping("/admin/category/save")
    public String saveCategory(Category category, RedirectAttributes attributes) {
        val result = categoryService.saveCategory(category);
        if (result.isSuccess()) {
            attributes.addFlashAttribute("info", "操作成功");
        } else {
            attributes.addFlashAttribute("error", result.getMsg() + ",保存寝室类型失败");
        }
        return "redirect:/admin/category-list";
    }

    @ApiOperation("ajax:根据若干id删除寝室类型")
    @RequestMapping(value = "/admin/category/delete", method = {RequestMethod.POST})
    @ResponseBody
    public LayuiResult<String> deleteCategoryByIds(String ids) {
        val idList = parseLongList(ids);
        if (categoryService.deleteCategoryByIds(idList).isSuccess()) {
            return new LayuiResult<>(SUCCESS, null, null);
        } else {
            return new LayuiResult<>(FAILED, null, null);
        }
    }
}

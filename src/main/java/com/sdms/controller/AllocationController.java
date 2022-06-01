package com.sdms.controller;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.LayuiResult;
import com.sdms.entity.Student;
import com.sdms.service.RoomAllocationService;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.sdms.common.result.LayuiResult.ResultCode.FAILED;
import static com.sdms.common.result.LayuiResult.ResultCode.SUCCESS;
import static com.sdms.common.util.StringUtils.parseStringList;

@Controller
public class AllocationController {

    @Resource
    private RoomAllocationService allocationService;

    @GetMapping(value = {"/admin/allocation-list"})
    public String toAdminAllocationList() {
        return "admin/allocation-list"; // Thymeleaf模板的名字,表示 templates/admin/allocation-list.html
    }

    @ApiOperation("ajax:分页查询学生住宿信息")
    @RequestMapping(value = "/admin/allocations", method = {RequestMethod.POST})
    @ResponseBody
    public Page<Student> fetchPage(@RequestBody PageRequest pageRequest) {
        return allocationService.fetchPage(pageRequest);
    }

    @ApiOperation("ajax:根据若干学号为学生完成解约")
    @RequestMapping(value = "/admin/allocation/release", method = {RequestMethod.POST})
    @ResponseBody
    public LayuiResult<String> releaseStudentByIds(String ids) {
        val idList = parseStringList(ids);
        if (allocationService.releaseStudentByIds(idList).isSuccess()) {
            return new LayuiResult<>(SUCCESS, null, null);
        } else {
            return new LayuiResult<>(FAILED, null, null);
        }
    }
}

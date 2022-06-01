package com.sdms.controller;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.LayuiResult;
import com.sdms.entity.RoomRequest;
import com.sdms.service.RoomRequestService;
import com.sdms.service.RoomService;
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
import static com.sdms.common.util.StringUtils.parseLongList;

@Api("住宿申请相关api")
@Controller
public class RoomRequestController {

    @Resource
    private RoomRequestService roomRequestService;

    @Resource
    private RoomService roomService;

    @Resource
    private StudentService studentService;

    @GetMapping(value = {"/admin/room-request-list"})
    public String toAdminRoomRequestList(Model model) {
        model.addAttribute("statuses", roomRequestService.listAllStatuses());
        return "admin/room-request-list"; // Thymeleaf模板的名字,表示 templates/admin/room-request-list.html
    }

    @ApiOperation("ajax:分页查询住宿申请信息")
    @RequestMapping(value = "/admin/room-requests", method = {RequestMethod.POST})
    @ResponseBody
    public Page<RoomRequest> fetchPage(@RequestBody PageRequest pageRequest) {
        return roomRequestService.fetchPage(pageRequest);
    }

    @ApiOperation("ajax:根据id查询住宿申请")
    @GetMapping("/admin/room-request/{id}")
    @ResponseBody
    public RoomRequest getRoomRequestById(@PathVariable Long id) {
        return roomRequestService.getRoomRequestById(id);
    }

    @ApiOperation("跳转到住宿申请编辑界面")
    @GetMapping("/admin/room-request/edit")
    public String toEditRoomRequestById(@RequestParam(defaultValue = "-1") long id, Model model) {
        val roomRequest = roomRequestService.getRoomRequestById(id);
        if (roomRequest != null) {
            model.addAttribute("operation", "编辑住宿申请");
            model.addAttribute("roomRequest", roomRequest);
            model.addAttribute("statuses", roomRequestService.listAllStatuses());
            model.addAttribute("students", studentService.listAllStudents());
            model.addAttribute("rooms", roomService.listAllRooms());
            return "admin/room-request-input";// Thymeleaf模板的名字,表示 templates/admin/room-request-input.html
        } else {
            return "redirect:/admin/room-request-list";
        }
    }

    @ApiOperation("跳转到住宿申请添加界面")
    @GetMapping("/admin/room-request/create")
    public String toCreateRoomRequest(Model model) {
        model.addAttribute("operation", "添加住宿申请");
        model.addAttribute("roomRequest", new RoomRequest());
        model.addAttribute("statuses", roomRequestService.listAllStatuses());
        model.addAttribute("students", studentService.listAllStudents());
        model.addAttribute("rooms", roomService.listAllRooms());
        return "admin/room-request-input";
    }

    @ApiOperation("保存住宿申请")
    @GetMapping("/admin/room-request/save")
    public String saveRoomRequest(RoomRequest roomRequest, RedirectAttributes attributes) {
        val result = roomRequestService.saveRoomRequest(roomRequest);
        if (result.isSuccess()) {
            attributes.addFlashAttribute("info", "操作成功");
        } else {
            attributes.addFlashAttribute("error", result.getMsg() + ",保存住宿申请失败");
        }
        return "redirect:/admin/room-request-list";
    }

    @ApiOperation("ajax:根据若干id删除住宿申请")
    @RequestMapping(value = "/admin/room-request/delete", method = {RequestMethod.POST})
    @ResponseBody
    public LayuiResult<String> deleteRoomRequestByIds(String ids) {
        val idList = parseLongList(ids);
        if (roomRequestService.deleteRoomRequestByIds(idList).isSuccess()) {
            return new LayuiResult<>(SUCCESS, null, null);
        } else {
            return new LayuiResult<>(FAILED, null, null);
        }
    }

    @ApiOperation("新增住宿申请")
    @GetMapping("/room-request/new")
    @ResponseBody
    public LayuiResult<String> addNewRoomRequest(String studentId, Long roomId) {
        if (roomRequestService.newRoomRequest(studentId, roomId).isSuccess()) {
            return new LayuiResult<>(SUCCESS, null, null);
        } else {
            return new LayuiResult<>(FAILED, null, null);
        }
    }
}

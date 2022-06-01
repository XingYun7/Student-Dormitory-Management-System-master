package com.sdms.controller;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.LayuiResult;
import com.sdms.entity.Room;
import com.sdms.service.CategoryService;
import com.sdms.service.RoomService;
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

@Api("寝室相关api")
@Controller
public class RoomController {

    @Resource
    private RoomService roomService;

    @Resource
    private CategoryService categoryService;

    @GetMapping(value = {"/admin/room-list"})
    public String toAdminRoomList(Model model) {
        model.addAttribute("categories", categoryService.listAllCategories());
        return "admin/room-list"; // Thymeleaf模板的名字,表示 templates/admin/room-list.html
    }

    @ApiOperation("ajax:分页查询寝室信息")
    @RequestMapping(value = "/admin/rooms", method = {RequestMethod.POST})
    @ResponseBody
    public Page<Room> fetchPage(@RequestBody PageRequest pageRequest) {
        return roomService.fetchPage(pageRequest);
    }

    @ApiOperation("ajax:根据id查询寝室")
    @GetMapping("/admin/room/{id}")
    @ResponseBody
    public Room getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @ApiOperation("跳转到寝室详情界面")
    @GetMapping("/admin/room/detail")
    public String toDisplayRoomDetailById(@RequestParam(defaultValue = "") Long id, Model model) {
        val room = roomService.getRoomWithStudentsById(id);
        if (room != null) {
            model.addAttribute("room", room);
            return "admin/room-detail";// Thymeleaf模板的名字,表示 templates/admin/room-detail.html
        } else {
            return "redirect:/admin/room-list";
        }
    }

    @ApiOperation("跳转到寝室编辑界面")
    @GetMapping("/admin/room/edit")
    public String toEditRoomById(@RequestParam(defaultValue = "-1") long id, Model model) {
        val room = roomService.getRoomById(id);
        if (room != null) {
            model.addAttribute("operation", "编辑寝室");
            model.addAttribute("room", room);
            model.addAttribute("categories", categoryService.listAllCategories());
            return "admin/room-input";// Thymeleaf模板的名字,表示 templates/admin/room-input.html
        } else {
            return "redirect:/admin/room-list";
        }
    }

    @ApiOperation("跳转到寝室添加界面")
    @GetMapping("/admin/room/create")
    public String toCreateRoom(Model model) {
        model.addAttribute("operation", "添加寝室");
        model.addAttribute("room", new Room());
        model.addAttribute("categories", categoryService.listAllCategories());
        return "admin/room-input";
    }

    @ApiOperation("保存寝室")
    @GetMapping("/admin/room/save")
    public String saveRoom(Room room, RedirectAttributes attributes) {
        val result = roomService.saveRoom(room);
        if (result.isSuccess()) {
            attributes.addFlashAttribute("info", "操作成功");
        } else {
            attributes.addFlashAttribute("error", result.getMsg() + ",保存寝室失败");
        }
        return "redirect:/admin/room-list";
    }

    @ApiOperation("ajax:根据若干id删除寝室")
    @RequestMapping(value = "/admin/room/delete", method = {RequestMethod.POST})
    @ResponseBody
    public LayuiResult<String> deleteRoomByIds(String ids) {
        val idList = parseLongList(ids);
        if (roomService.deleteRoomByIds(idList).isSuccess()) {
            return new LayuiResult<>(SUCCESS, null, null);
        } else {
            return new LayuiResult<>(FAILED, null, null);
        }
    }
}

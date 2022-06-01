package com.sdms.controller;

import com.sdms.common.result.LayuiResult;
import com.sdms.config.PictureConfig;
import com.sdms.common.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;

@Api("图片文件上传api")
@Controller
@Slf4j
public class FileUploadController {

    private final static String NAME = "file";

    @Resource
    private PictureConfig pictureConfig;

    /**
     * 本地图片文件上传接口 "/upload"
     *
     * @param request 图片文件上传请求,要求参数名是 file, (例如：用原生form提交,input标签需要添加 name="file" )
     * @return JSON格式的对象, code == 0 表示上传成功 , code == 1 表示上传失败
     */
    @ApiOperation("ajax:本地图片文件上传")
    @PostMapping("/upload")
    @ResponseBody
    public LayuiResult<Object> upload(HttpServletRequest request) {
        MultipartHttpServletRequest mRequest;
        if (request instanceof MultipartHttpServletRequest) {
            mRequest = (MultipartHttpServletRequest) request;
        } else {
            return result(1, "failure:请求异常", null);
        }
        val multipartFile = mRequest.getFile(NAME);
        if (null == multipartFile) {
            return result(1, "failure:参数异常,请检查参数名是否为" + NAME, null);
        }
        val originalFilename = multipartFile.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)) {
            return result(1, "failure:文件名为空", null);
        }
        val path = pictureConfig.getPath();
        val dest = new File(path + originalFilename);
        if (!dest.getParentFile().exists()) {
            if (!dest.getParentFile().mkdirs()) {
                return result(1, "failure:服务器存储路径创建失败", null);
            }
        }
        try {
            multipartFile.transferTo(dest);
        } catch (Exception e) {
            log.error("文件上传失败: error = " + e.getMessage());
            return result(1, "failure:文件保存失败", null);
        }
        return result(0, "success", "/sdms-images/" + originalFilename);
    }

    private LayuiResult<Object> result(Integer code, String msg, String pictureURL) {
        val m = new HashMap<String, Object>();
        m.put("pictureURL", pictureURL);
        return new LayuiResult<>(code, msg, null, Collections.singletonList(m));
    }

}
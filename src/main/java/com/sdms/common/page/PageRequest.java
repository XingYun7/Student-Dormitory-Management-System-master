package com.sdms.common.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 为前端Layui的Table组件设计的分页请求结构
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页请求")
public class PageRequest {

    @ApiModelProperty("页号,从1开始计数")
    private long page = 1;

    @ApiModelProperty("每一页的容量")
    private long limit = 10;

    @ApiModelProperty("0表示模糊查询,其它表示精确查询")
    private int queryType = 0;

    @ApiModelProperty("其它查询参数")
    private Map<String, String> param = new HashMap<>();

}

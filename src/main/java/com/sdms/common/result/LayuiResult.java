package com.sdms.common.result;

import com.sdms.common.page.Page;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * LayuiResult的本质是一种VO,
 * 这是专门为前端Layui的Table组件设计的JSON返回值结构,能够应对其分页需求
 *
 * @param <T> data数组中保存的实体类型
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页响应")
public class LayuiResult<T> implements Serializable {

    /**
     * 返回给前端的状态码
     * 注: 0 --- 成功 、 1 --- 失败 、...
     */
    private int code;

    /**
     * 对响应结果的描述
     */
    private String msg;

    /**
     * 实体在数据库中的总条数
     * 注:若不为null,则可与data配合完成分页
     */
    private Long count;

    /**
     * 当前页的若干实体
     * 注: 返回到前端的data是数组类型,可能为空数组,但永不为null
     */
    private Collection<T> data;

    public LayuiResult(ResultCode resultCode, Long count, Collection<T> data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.count = count;
        this.data = data;
    }

    public LayuiResult(ResultCode resultCode, Page<T> page) {
        this(resultCode, page.getTotalCount(), page.getContent());
    }

    public LayuiResult(Page<T> page) {
        this(ResultCode.SUCCESS, page);
    }

    @Getter
    public enum ResultCode {

        //通用的接口返回值
        SUCCESS(0, "操作成功"),// layui默认将0作为成功对应的状态码
        FAILED(1, "接口错误"),
        VALIDATE_FAILED(2, "参数校验失败"),
        ERROR(3, "未知错误"),

        //与用户相关的接口返回值
        USER_NOT_EXIST(4, "用户不存在"),
        USER_LOGIN_FAIL(5, "用户名或密码错误"),
        USER_NOT_LOGIN(6, "用户还未登录,请先登录"),
        NO_PERMISSION(7, "权限不足,请联系管理员");

        private final int code;
        private final String msg;

        ResultCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

}

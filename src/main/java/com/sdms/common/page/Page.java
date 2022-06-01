package com.sdms.common.page;

import com.querydsl.core.QueryResults;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;

/**
 * 为Layui设计的分页对象,本质上是一种 DTO,
 * 如果在controller层将Page作为方法返回值,
 * 最终会在Advice中被包装成LayuiResult对象返回给前端
 *
 * @param <E> 实体的类型
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("分页")
public class Page<E> {

    /**
     * 实体在数据库中的总数
     */
    @ApiModelProperty("实体在数据库中的总数")
    long totalCount;

    /**
     * 当前分页包含的若干个实体
     */
    @ApiModelProperty("当前分页包含的若干实体")
    Collection<E> content;

    /**
     * @param result queryDSL调用fetchResults获得的查询结果
     * @param <E>    实体
     * @return 返回一个包含若干实体的分页
     */
    public static <E> Page<E> of(QueryResults<E> result) {
        Page<E> page = new Page<>();
        page.totalCount = result.getTotal();
        page.content = result.getResults();
        return page;
    }

    /**
     * @param <E> 实体
     * @return 返回一个空页
     */
    public static <E> Page<E> empty() {
        Page<E> page = new Page<>();
        page.totalCount = 0;
        page.content = Collections.emptyList();
        return page;
    }
}

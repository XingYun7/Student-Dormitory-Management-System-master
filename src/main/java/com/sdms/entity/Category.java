package com.sdms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 寝室类型
 */
@Getter
@Setter
@Entity
@Table(name = "t_category")
@ApiModel("寝室类型")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20) comment '寝室类型的主键'")
    @ApiModelProperty("主键")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '寝室类型的名称'")
    @ApiModelProperty("名称")
    private String name;

    @Column(nullable = false, columnDefinition = "bigint(20) comment '寝室可容纳学生人数的最大值'")
    @ApiModelProperty("可容纳学生人数的最大值")
    private Long maxSize;

    //一个寝室类型对应的多个寝室
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "category")
    @JsonIgnore
    private Set<Room> rooms;

}

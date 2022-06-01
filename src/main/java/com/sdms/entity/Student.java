package com.sdms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 学生
 */
@Getter
@Setter
@Entity
@Table(name = "t_student")
@ApiModel("学生")
public class Student {

    @Id
    @Column(nullable = false, columnDefinition = "varchar(255) comment '学号,即学生的主键'")
    @ApiModelProperty("学号,即主键")
    private String id; // 学号用字符串表示

    @Column(nullable = false, columnDefinition = "varchar(255) comment '学生的姓名'")
    @ApiModelProperty("姓名")
    private String name;

    // 学生对应的用户
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    // 每个学生有自己的班级,多个学生可以有同一个班级
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teaching_class_id", referencedColumnName = "id")
    @JsonIgnore
    private TeachingClass teachingClass;

    // 每个学生有自己的寝室,多个学生可以有同一个寝室
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonIgnore
    private Room room;

    @Transient
    @ApiModelProperty("临时字段:用户id")
    private Long userId;

    @Transient
    @ApiModelProperty("临时字段:账号")
    private String username;

    @Transient
    @ApiModelProperty("临时字段:班级id")
    private Long teachingClassId;

    @Transient
    @ApiModelProperty("临时字段:班级名称")
    private String teachingClassName;

    @Transient
    @ApiModelProperty("临时字段:寝室id")
    private Long roomId;

    @Transient
    @ApiModelProperty("临时字段:寝室名称")
    private String roomName;

    @Transient
    @ApiModelProperty("临时字段:联系方式")
    private String phone;

}

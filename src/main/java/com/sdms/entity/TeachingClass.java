package com.sdms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 教学班级
 */
@Getter
@Setter
@Entity
@Table(name = "t_class")
@ApiModel("学生的班级")
public class TeachingClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20) comment '教学班级的主键'")
    @ApiModelProperty("主键")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '教学班级的名称'")
    @ApiModelProperty("名称")
    private String name;

    // 一个班级有多个学生
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teachingClass")
    @JsonIgnore
    private Set<Student> students;

}

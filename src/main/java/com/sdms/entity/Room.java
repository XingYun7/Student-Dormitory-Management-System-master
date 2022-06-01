package com.sdms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 寝室
 */
@Getter
@Setter
@Entity
@Table(name = "t_room")
@ApiModel("寝室")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20) comment '寝室的主键'")
    @ApiModelProperty("主键")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '寝室的名称'")
    @ApiModelProperty("名称")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '寝室的照片'")
    @ApiModelProperty("照片")
    private String picture;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '寝室的地址'")
    @ApiModelProperty("地址")
    private String address;

    //每个寝室都有自己的类型,不同的寝室可以是同一种类型
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    //一个寝室中居住有多名学生
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
    private Set<Student> students;

    @Transient
    @ApiModelProperty("临时字段:寝室类型id")
    private Long categoryId;

    @Transient
    @ApiModelProperty("临时字段:寝室类型名称")
    private String categoryName;

    @Transient
    @ApiModelProperty("临时字段:住在该寝室的所有学生的姓名和学号,格式为 姓名1|学号1,姓名2|学号2,...")
    private String studentsNameAndId;

}

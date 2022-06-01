package com.sdms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 角色
 */
@Getter
@Setter
@Entity
@Table(name = "t_role")
@ApiModel("角色")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20) comment '角色的主键'")
    @ApiModelProperty("主键")
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "varchar(255) comment '角色的名称'")
    @ApiModelProperty("名称")
    private String name;

    // 一个角色可以被多个用户使用
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    @JsonIgnore
    private Set<User> users;

    // 角色和权限之间的关系是多对多
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_role_permission",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Permission> permissions;

}

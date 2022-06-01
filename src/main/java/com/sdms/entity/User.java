package com.sdms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 用户
 */
@Getter
@Setter
@Entity
@Table(name = "t_user")
@ApiModel("用户")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20) comment '用户的主键'")
    @ApiModelProperty("主键")
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "varchar(255) comment '用于登录的账号'")
    @ApiModelProperty("账号,即用户名")
    private String username;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '数据库中存储的密码'")
    @ApiModelProperty("密码")
    @JsonIgnore // 密码不应该在前台展示
    private String password;

    @Column(columnDefinition = "varchar(255) comment '用户的头像,数据库中仅存储文件路径'")
    @ApiModelProperty("头像")
    private String avatar;

    @Column(columnDefinition = "varchar(255) comment '用户的联系方式'")
    @ApiModelProperty("联系方式")
    private String phone;

    @Column(columnDefinition = "varchar(255) comment '用户的地址'")
    @ApiModelProperty("地址")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'MALE' comment '用户的性别'")
    @ApiModelProperty("性别")
    @JsonIgnore
    private Gender gender;

    /**
     * 每个用户有自己的角色,多个用户可以有同一个角色
     * CascadeType见 https://www.jianshu.com/p/e8caafce5445
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ApiModelProperty("角色")
    @JsonIgnore
    private Role role;

    @Getter
    public enum Gender {

        MALE("男"),
        FEMALE("女");

        private final String value;

        Gender(String value) {
            this.value = value;
        }

        public static Gender of(String genderStr) {
            for (Gender gender : values()) {
                if (gender.value.equals(genderStr)) {
                    return gender;
                }
            }
            return null;
        }
    }

    @Transient
    @ApiModelProperty("临时字段:角色名称")
    private String roleName;

    @Transient
    @ApiModelProperty("临时字段:角色id")
    private Long roleId;

    @Transient
    @ApiModelProperty("临时字段:用字符串描述的性别")
    private String genderStr;

}

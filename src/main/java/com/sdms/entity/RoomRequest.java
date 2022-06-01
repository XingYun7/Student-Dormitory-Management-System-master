package com.sdms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 住宿申请
 */
@Getter
@Setter
@Entity
@Table(name = "t_room_request")
@ApiModel("住宿申请")
public class RoomRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20) comment '住宿申请的主键'")
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 住宿申请的状态包括三种: "未处理"  "成功"  "被拒绝,失败"
     * 这里用枚举表示,数据库中存储字符串
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'NO_HANDLE' comment '住宿申请的状态'")
    @JsonIgnore
    private RoomRequestStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @JsonIgnore
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonIgnore
    private Room room;

    @Getter
    public enum RoomRequestStatus {

        NO_HANDLE(0, "未处理"),
        SUCCESS(1, "成功分配宿舍"),
        FAILURE(2, "拒绝分配宿舍");

        private final int code;

        private final String message;

        RoomRequestStatus(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public static RoomRequestStatus valueOfCode(int code) {
            for (RoomRequestStatus value : values()) {
                if (value.code == code) {
                    return value;
                }
            }
            return null;
        }
    }

    @Transient
    @ApiModelProperty("临时字段:处理状态编码")
    private int statusCode;

    @Transient
    @ApiModelProperty("临时字段:处理状态描述")
    private String statusMsg;

    @Transient
    @ApiModelProperty("临时字段:学生id,即学号")
    private String studentId;

    @Transient
    @ApiModelProperty("临时字段:学生姓名")
    private String studentName;

    @Transient
    @ApiModelProperty("临时字段:寝室id")
    private Long roomId;

    @Transient
    @ApiModelProperty("临时字段:寝室名称")
    private String roomName;

}

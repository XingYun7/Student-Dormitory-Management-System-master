/*
 Navicat Premium Data Transfer

 Source Server         : 本地MySQL56
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : localhost:3306
 Source Schema         : db_sdms

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : 65001

 Date: 23/07/2021 18:10:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '寝室类型的主键',
  `max_size` bigint(20) NOT NULL COMMENT '寝室可容纳学生人数的最大值',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '寝室类型的名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_category
-- ----------------------------
INSERT INTO `t_category` VALUES (13, 4, '四人寝');
INSERT INTO `t_category` VALUES (14, 6, '六人寝');
INSERT INTO `t_category` VALUES (15, 10, '十人寝');

-- ----------------------------
-- Table structure for t_class
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '教学班级的主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教学班级的名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_class
-- ----------------------------
INSERT INTO `t_class` VALUES (1, '计算机1701');
INSERT INTO `t_class` VALUES (2, '计算机1702');
INSERT INTO `t_class` VALUES (3, '计算机1703');
INSERT INTO `t_class` VALUES (4, '计算机1704');
INSERT INTO `t_class` VALUES (5, '计算机1705');

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限的主键',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限码',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限的描述',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限的名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_permission
-- ----------------------------

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色的主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色的名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_bkpm7njy2ort1yoiddc7jg8gj`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '学生');
INSERT INTO `t_role` VALUES (2, '管理员');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色的主键',
  `permission_id` bigint(20) NOT NULL COMMENT '权限的主键',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
  INDEX `FKjobmrl6dorhlfite4u34hciik`(`permission_id`) USING BTREE,
  CONSTRAINT `FK90j038mnbnthgkc17mqnoilu9` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKjobmrl6dorhlfite4u34hciik` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for t_room
-- ----------------------------
DROP TABLE IF EXISTS `t_room`;
CREATE TABLE `t_room`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '寝室的主键',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '寝室的地址',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '寝室的名称',
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '寝室的照片',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '寝室类型的主键',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKtr387rfoo7r0y0aymvnqb9gso`(`category_id`) USING BTREE,
  CONSTRAINT `FKtr387rfoo7r0y0aymvnqb9gso` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_room
-- ----------------------------
INSERT INTO `t_room` VALUES (1, '湖北省武汉市', '310', '/sdms-images/room-1.jpg', 13);
INSERT INTO `t_room` VALUES (2, '湖北省武汉市', '312', '/sdms-images/room-2.jpg', 13);
INSERT INTO `t_room` VALUES (3, '湖北省武汉市', '313', '', 13);

-- ----------------------------
-- Table structure for t_room_request
-- ----------------------------
DROP TABLE IF EXISTS `t_room_request`;
CREATE TABLE `t_room_request`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '住宿申请的主键',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'NO_HANDLE' COMMENT '住宿申请的状态',
  `room_id` bigint(20) NULL DEFAULT NULL COMMENT '寝室的主键',
  `student_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学号,即学生的主键',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKj02ld97dsteo49cacmpe23tb7`(`room_id`) USING BTREE,
  INDEX `FKhi32lfryi65nboj3uk1nlseec`(`student_id`) USING BTREE,
  CONSTRAINT `FKhi32lfryi65nboj3uk1nlseec` FOREIGN KEY (`student_id`) REFERENCES `t_student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKj02ld97dsteo49cacmpe23tb7` FOREIGN KEY (`room_id`) REFERENCES `t_room` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_room_request
-- ----------------------------
INSERT INTO `t_room_request` VALUES (6, 'SUCCESS', 1, '01217');
INSERT INTO `t_room_request` VALUES (7, 'FAILURE', 2, '01218');
INSERT INTO `t_room_request` VALUES (8, 'SUCCESS', 3, '01218');
INSERT INTO `t_room_request` VALUES (9, 'SUCCESS', 1, '01219');
INSERT INTO `t_room_request` VALUES (10, 'FAILURE', 2, '01219');
INSERT INTO `t_room_request` VALUES (11, 'NO_HANDLE', 2, '09999');

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学号,即学生的主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生的姓名',
  `room_id` bigint(20) NULL DEFAULT NULL COMMENT '寝室的主键',
  `teaching_class_id` bigint(20) NULL DEFAULT NULL COMMENT '教学班级的主键',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户的主键',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKeq2uq5830o85jo7xg036j471c`(`room_id`) USING BTREE,
  INDEX `FKt7sym0pjqh22ky0n4jsduj90u`(`teaching_class_id`) USING BTREE,
  INDEX `FKpyiblhnxi2vmb6bgpdwqqombu`(`user_id`) USING BTREE,
  CONSTRAINT `FKeq2uq5830o85jo7xg036j471c` FOREIGN KEY (`room_id`) REFERENCES `t_room` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKpyiblhnxi2vmb6bgpdwqqombu` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKt7sym0pjqh22ky0n4jsduj90u` FOREIGN KEY (`teaching_class_id`) REFERENCES `t_class` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('01217', '张三', 1, 1, 9);
INSERT INTO `t_student` VALUES ('01218', '李四', 3, 1, 10);
INSERT INTO `t_student` VALUES ('01219', '王五', NULL, 2, 11);
INSERT INTO `t_student` VALUES ('09999', '小黑', NULL, 2, 12);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户的主键',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户的地址',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户的头像,数据库中仅存储文件路径',
  `gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'MALE' COMMENT '用户的性别',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据库中存储的密码',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户的联系方式',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用于登录的账号',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色的主键',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_jhib4legehrm4yscx9t3lirqi`(`username`) USING BTREE,
  INDEX `FK76cs7cu4h4y8vc1vd4qyw36rt`(`role_id`) USING BTREE,
  CONSTRAINT `FK76cs7cu4h4y8vc1vd4qyw36rt` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '湖北省武汉市', '/sdms-images/avatar-3.jpg', 'MALE', '0192023a7bbd73250516f069df18b500', '15566667777', 'admin', 2);
INSERT INTO `t_user` VALUES (9, '北京市', '/sdms-images/avatar-2.jpg', 'MALE', 'b5b6e0fc8e3ae07753dbf8793275881e', '15512341234', '01217', 1);
INSERT INTO `t_user` VALUES (10, '天津市', '/sdms-images/avatar-3.jpg', 'FEMALE', '783011afda4fee11b6ee2cff303da8cb', '18812341234', '01218', 1);
INSERT INTO `t_user` VALUES (11, '重庆市', '/sdms-images/avatar-4.jpg', 'FEMALE', 'eaa6a59c9212e6c319c6701991a38509', '17712341234', '01219', 1);
INSERT INTO `t_user` VALUES (12, NULL, NULL, NULL, '0908fba1949e44d7584101b868d8474c', NULL, '09999', 1);

SET FOREIGN_KEY_CHECKS = 1;

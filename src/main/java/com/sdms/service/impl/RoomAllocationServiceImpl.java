package com.sdms.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.dao.StudentDao;
import com.sdms.entity.Student;
import com.sdms.qmodel.QRoom;
import com.sdms.qmodel.QStudent;
import com.sdms.qmodel.QTeachingClass;
import com.sdms.qmodel.QUser;
import com.sdms.service.RoomAllocationService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

import static com.sdms.common.result.OperationResult.failure;
import static com.sdms.common.result.OperationResult.success;
import static com.sdms.common.util.StringUtils.isBlankOrNull;
import static com.sdms.common.util.StringUtils.trimAllWhitespace;

@Slf4j
@Service
public class RoomAllocationServiceImpl implements RoomAllocationService {

    @Resource
    private StudentDao studentDao;

    @Resource
    private JPAQueryFactory queryFactory;

    @Override
    public Page<Student> fetchPage(PageRequest request) {
        val keyWord = trimAllWhitespace(request.getParam().get("keyWord"));
        val student = QStudent.student;
        val room = QRoom.room;
        val teachingClass = QTeachingClass.teachingClass;
        val user = QUser.user;
        // 动态拼接查询条件
        val condition = new BooleanBuilder();
        switch (request.getQueryType()) {
            case 0: // 按学生姓名查找
                if (!isBlankOrNull(keyWord)) {
                    condition.and(student.name.like("%" + keyWord + "%"));
                }
                break;
            case 1: // 按寝室查找
                if (!isBlankOrNull(keyWord)) {
                    condition.and(room.name.like("%" + keyWord + "%"));
                }
                break;
            case 2: // 按班级查找
                if (!isBlankOrNull(keyWord)) {
                    condition.and(teachingClass.name.like("%" + keyWord + "%"));
                }
                break;
            default:
                break;
        }
        condition.and(student.room.isNotNull());
        val query = queryFactory
                .select(Projections.bean(Student.class,
                        student.id,
                        student.name,
                        teachingClass.id.as("teachingClassId"),
                        teachingClass.name.as("teachingClassName"),
                        room.id.as("roomId"),
                        room.name.as("roomName"),
                        user.phone.as("phone")))
                .from(student)
                .leftJoin(room).on(student.room.id.eq(room.id))
                .leftJoin(teachingClass).on(student.teachingClass.id.eq(teachingClass.id))
                .leftJoin(user).on(student.user.id.eq(user.id))
                .orderBy(student.id.asc())
                .where(condition)
                .offset((request.getPage() - 1) * request.getLimit()).limit(request.getLimit());
        // 执行分页查询
        val result = query.fetchResults();
        return Page.of(result);
    }

    @Override
    public OperationResult<String> releaseStudentByIds(Collection<String> ids) {
        try {
            val studentList = studentDao.findAllById(ids);
            for (Student student : studentList) {
                student.setRoom(null);
            }
            studentDao.saveAll(studentList);
        } catch (Exception e) {
            log.error("解约失败,ids=" + ids + " error:" + e.getMessage());
            return failure("解约失败");
        }
        return success("解约成功");
    }
}

package com.sdms.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.dao.RoomDao;
import com.sdms.entity.Room;
import com.sdms.qmodel.QCategory;
import com.sdms.qmodel.QRoom;
import com.sdms.qmodel.QStudent;
import com.sdms.qmodel.QUser;
import com.sdms.service.RoomService;
import com.sdms.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.sdms.common.result.OperationResult.failure;
import static com.sdms.common.result.OperationResult.success;
import static com.sdms.common.util.StringUtils.*;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    @Resource
    private RoomDao roomDao;

    @Resource
    private SessionService sessionService;

    @Resource
    private JPAQueryFactory queryFactory;

    @Override
    public void fillTransientFields(Room room) {
        val category = room.getCategory();
        if (category != null) {
            room.setCategoryId(category.getId());
            room.setCategoryName(category.getName());
        }
    }

    @Override
    public Page<Room> fetchPage(PageRequest request) {
        boolean vague = request.getQueryType() == 0; // 0 表示模糊查询
        val param = request.getParam();
        val roomId = parseLong(param.get("roomId"));
        val categoryId = parseLong(param.get("categoryId"));
        val keyWord = trimAllWhitespace(param.get("keyWord"));
        val room = QRoom.room;
        val category = QCategory.category;
        // 动态拼接查询条件
        val condition = new BooleanBuilder();
        if (categoryId != null) {
            condition.and(category.id.eq(categoryId));
        }
        val subCondition = new BooleanBuilder();
        if (roomId != null) {
            subCondition.or(room.id.eq(roomId));
        }
        if (!isBlankOrNull(keyWord)) {
            if (vague) {
                // 模糊查询
                subCondition.or(room.name.like("%" + keyWord + "%"));
                subCondition.or(room.address.like("%" + keyWord + "%"));
            } else {
                // 精确查询
                subCondition.or(room.name.eq(keyWord));
                subCondition.or(room.address.eq(keyWord));
            }
        }
        condition.and(subCondition);
        val query = queryFactory
                .select(Projections.bean(Room.class,
                        room.id,
                        room.name,
                        room.address,
                        room.picture,
                        category.id.as("categoryId"),
                        category.name.as("categoryName")))
                .from(room).leftJoin(category).on(room.category.id.eq(category.id))
                .orderBy(room.id.asc())
                .where(condition)
                .offset((request.getPage() - 1) * request.getLimit()).limit(request.getLimit());
        // 执行分页查询
        val result = query.fetchResults();
        val roomIdList = new ArrayList<Long>();
        result.getResults().forEach(r -> roomIdList.add(r.getId()));
        val student = QStudent.student;
        final List<Tuple> tupleList = queryFactory
                .select(student.id, student.name, room.id)
                .from(student).leftJoin(room).on(student.room.id.eq(room.id))
                .where(student.room.id.in(roomIdList))
                .fetch();
        for (Room r : result.getResults()) {
            fillStudentsNameAndId(r, room, student, tupleList);
        }
        return Page.of(result);
    }

    @Override
    public Room getRoomById(Long id) {
        val optional = roomDao.findById(id);
        if (optional.isPresent()) {
            val room = optional.get();
            this.fillTransientFields(room);
            return room;
        }
        return null;
    }

    @Override
    public OperationResult<Room> saveRoom(Room room) {
        if (room == null) {
            return failure("寝室为null");
        }
        val name = room.getName();
        if (isBlankOrNull(name)) {
            return failure("寝室名称不可以为空或者空白串");
        }
        return success(roomDao.save(room));
    }

    @Override
    public OperationResult<String> deleteRoomByIds(Collection<Long> ids) {
        try {
            roomDao.deleteRoomsByIdIn(ids);
        } catch (Exception e) {
            log.error("寝室删除失败,ids=" + ids + ",error is " + e.getMessage());
            return failure("删除失败");
        }
        return success("删除成功");
    }

    @Override
    public List<Room> listAllRooms() {
        return roomDao.findAll();
    }

    @Override
    public Room getCurrentStudentRoom() {
        val currentUser = sessionService.getCurrentUser();
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        val room = QRoom.room;
        val student = QStudent.student;
        val user = QUser.user;
        return queryFactory.selectFrom(room)
                .rightJoin(student).on(student.room.id.eq(room.id))
                .rightJoin(user).on(student.user.id.eq(user.id))
                .where(user.id.eq(currentUser.getId())).fetchOne();
    }

    @Override
    public long countStudentsByRoomId(Long id) {
        val student = QStudent.student;
        return queryFactory.selectFrom(student).where(student.room.id.eq(id)).fetchCount();
    }

    @Override
    public Room getRoomWithStudentsById(Long id) {
        val obj = getRoomById(id);
        if (obj == null) {
            return null;
        }
        val room = QRoom.room;
        val student = QStudent.student;
        final List<Tuple> tupleList = queryFactory
                .select(student.id, student.name, room.id)
                .from(student).leftJoin(room).on(student.room.id.eq(room.id))
                .where(room.id.eq(obj.getId()))
                .fetch();
        fillStudentsNameAndId(obj, room, student, tupleList);
        return obj;
    }

    private void fillStudentsNameAndId(Room obj, QRoom room, QStudent student, List<Tuple> tupleList) {
        val stringList = new ArrayList<String>();
        for (Tuple tuple : tupleList) {
            if (Objects.equals(tuple.get(room.id), obj.getId())) {
                stringList.add(tuple.get(student.name) + "|" + tuple.get(student.id));
            }
        }
        obj.setStudentsNameAndId(String.join(",", stringList));
    }
}

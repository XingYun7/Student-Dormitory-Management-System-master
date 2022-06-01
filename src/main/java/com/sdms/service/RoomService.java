package com.sdms.service;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.entity.Room;

import java.util.Collection;
import java.util.List;

public interface RoomService extends BaseEntityService<Room> {

    Page<Room> fetchPage(PageRequest pageRequest);

    Room getRoomById(Long id);

    OperationResult<Room> saveRoom(Room room);

    OperationResult<String> deleteRoomByIds(Collection<Long> ids);

    List<Room> listAllRooms();

    Room getCurrentStudentRoom();

    long countStudentsByRoomId(Long id);

    Room getRoomWithStudentsById(Long id);
}
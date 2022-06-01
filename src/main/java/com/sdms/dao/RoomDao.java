package com.sdms.dao;

import com.sdms.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Collection;

public interface RoomDao extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    @Modifying
    @Transactional
    void deleteRoomsByIdIn(Collection<Long> ids);

}

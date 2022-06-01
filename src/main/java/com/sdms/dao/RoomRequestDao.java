package com.sdms.dao;

import com.sdms.entity.RoomRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Collection;

public interface RoomRequestDao extends JpaRepository<RoomRequest, Long>, JpaSpecificationExecutor<RoomRequest> {

    int countByStatusEquals(RoomRequest.RoomRequestStatus roomRequestStatus);

    @Modifying
    @Transactional
    void deleteRoomRequestsByIdIn(Collection<Long> ids);

}

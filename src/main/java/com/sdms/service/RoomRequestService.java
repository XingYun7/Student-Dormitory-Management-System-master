package com.sdms.service;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.entity.RoomRequest;

import java.util.Collection;
import java.util.List;

public interface RoomRequestService extends BaseEntityService<RoomRequest> {

    int getNoHandleCount();

    List<RoomRequest.RoomRequestStatus> listAllStatuses();

    Page<RoomRequest> fetchPage(PageRequest pageRequest);

    RoomRequest getRoomRequestById(Long id);

    OperationResult<RoomRequest> saveRoomRequest(RoomRequest roomRequest);

    OperationResult<String> deleteRoomRequestByIds(Collection<Long> ids);

    OperationResult<RoomRequest> newRoomRequest(String studentId, Long roomId);
}
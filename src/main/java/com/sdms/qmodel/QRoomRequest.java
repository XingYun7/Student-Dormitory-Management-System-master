package com.sdms.qmodel;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import com.sdms.entity.RoomRequest;


/**
 * QRoomRequest is a Querydsl query type for RoomRequest
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoomRequest extends EntityPathBase<RoomRequest> {

    private static final long serialVersionUID = 1284247987L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoomRequest roomRequest = new QRoomRequest("roomRequest");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QRoom room;

    public final EnumPath<RoomRequest.RoomRequestStatus> status = createEnum("status", RoomRequest.RoomRequestStatus.class);

    public final QStudent student;

    public QRoomRequest(String variable) {
        this(RoomRequest.class, forVariable(variable), INITS);
    }

    public QRoomRequest(Path<? extends RoomRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoomRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoomRequest(PathMetadata metadata, PathInits inits) {
        this(RoomRequest.class, metadata, inits);
    }

    public QRoomRequest(Class<? extends RoomRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
        this.student = inits.isInitialized("student") ? new QStudent(forProperty("student"), inits.get("student")) : null;
    }

}


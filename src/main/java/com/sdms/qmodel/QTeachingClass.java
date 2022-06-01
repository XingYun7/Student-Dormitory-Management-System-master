package com.sdms.qmodel;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import com.sdms.entity.Student;
import com.sdms.entity.TeachingClass;


/**
 * QTeachingClass is a Querydsl query type for TeachingClass
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTeachingClass extends EntityPathBase<TeachingClass> {

    private static final long serialVersionUID = -546241814L;

    public static final QTeachingClass teachingClass = new QTeachingClass("teachingClass");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final SetPath<Student, QStudent> students = this.<Student, QStudent>createSet("students", Student.class, QStudent.class, PathInits.DIRECT2);

    public QTeachingClass(String variable) {
        super(TeachingClass.class, forVariable(variable));
    }

    public QTeachingClass(Path<? extends TeachingClass> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeachingClass(PathMetadata metadata) {
        super(TeachingClass.class, metadata);
    }

}

